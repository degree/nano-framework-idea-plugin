package by.degree.learn.idea.plugin.nano.framework

import by.degree.learn.idea.plugin.nano.framework.FrameworkHelper.isComponent
import by.degree.learn.idea.plugin.nano.framework.FrameworkHelper.isPartOfFramework
import com.intellij.codeInsight.AnnotationUtil.isAnnotated
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiField
import org.jetbrains.uast.*

class LineMarkerProvider : RelatedItemLineMarkerProvider() {

    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        val e = getUParentForIdentifier(element)
        if (isPostConstruct(e)
            || isFieldInjection(e)
            || isFramework(e)
            || isComponent(e)
            || isFrameworkCall(e)
        ) result.add(markerFor(element))
    }

    private fun isFrameworkCall(element: UElement?): Boolean {
        return if (element is UQualifiedReferenceExpression) {
            val cls = element.getParentOfType<UCallExpression>()
                ?.resolve()
                ?.parent as? PsiClass
            cls?.let(FrameworkHelper::isPartOfFramework) == true
        } else {
            false
        }
    }

    private fun isComponent(element: UElement?) = element is UClass && isComponent(element.javaPsi)

    private fun isFramework(element: UElement?) = element is UClass && isPartOfFramework(element.javaPsi)

    private fun isFieldInjection(element: UElement?) =
        element is UField
                && element.javaPsi is PsiField
                && isAnnotated(element.javaPsi as PsiField, ANTS_INJECT, 0)

    private fun isPostConstruct(element: UElement?) =
        element is UMethod && isAnnotated(element.javaPsi, ANT_POST_CONSTRUCT, 0)

    private fun markerFor(element: PsiElement): RelatedItemLineMarkerInfo<PsiElement> {
        return NavigationGutterIconBuilder
            .create(Icons.GHILL)
            .setAlignment(GutterIconRenderer.Alignment.LEFT)
            .setTargets(element)
            .createLineMarkerInfo(element)
    }
}
