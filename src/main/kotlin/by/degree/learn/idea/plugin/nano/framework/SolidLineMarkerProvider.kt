package by.degree.learn.idea.plugin.nano.framework

import com.intellij.codeInsight.AnnotationUtil
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiField
import org.jetbrains.uast.*

class SolidLineMarkerProvider : RelatedItemLineMarkerProvider() {
    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<PsiElement>>
    ) {
        val uElement = getUParentForIdentifier(element)
        if (isPostConstruct(uElement)
            || isFieldInjection(uElement)
            || isFramework(uElement)
            || isComponent(uElement)
            || isFrameworkCall(uElement)
        ) result.add(markerFor(element))
    }

    private fun isFrameworkCall(uElement: UElement?) =
        uElement is UQualifiedReferenceExpression && (uElement.getParentOfType<UCallExpression>()
            ?.resolve()?.parent as? PsiClass)?.let { FrameworkHelper.isPartOfFramework(it) } == true

    private fun isComponent(uElement: UElement?) =
        uElement is UClass && FrameworkHelper.isComponent(uElement.javaPsi)

    private fun isFramework(uElement: UElement?) =
        uElement is UClass && FrameworkHelper.isPartOfFramework(uElement.javaPsi)

    private fun isFieldInjection(uElement: UElement?) =
        uElement is UField && uElement.javaPsi is PsiField && AnnotationUtil.isAnnotated(
            uElement.javaPsi as PsiField,
            ANTS_INJECT,
            0
        )

    private fun isPostConstruct(uElement: UElement?) =
        uElement is UMethod && AnnotationUtil.isAnnotated(uElement.javaPsi, ANT_POST_CONSTRUCT, 0)

    private fun markerFor(element: PsiElement): RelatedItemLineMarkerInfo<PsiElement> {
        return NavigationGutterIconBuilder
            .create(Icons.GHILL)
            .setAlignment(GutterIconRenderer.Alignment.LEFT)
            .setTargets(element)
            .createLineMarkerInfo(element)
    }
}
