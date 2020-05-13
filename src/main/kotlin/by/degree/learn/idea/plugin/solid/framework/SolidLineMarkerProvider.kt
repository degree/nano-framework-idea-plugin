package by.degree.learn.idea.plugin.solid.framework

import com.intellij.codeInsight.AnnotationUtil
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiField
import org.jetbrains.uast.UField
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.getUParentForIdentifier

class SolidLineMarkerProvider : RelatedItemLineMarkerProvider() {
    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<PsiElement>>
    ) {
        val uElement = getUParentForIdentifier(element)
        if (uElement is UMethod && AnnotationUtil.isAnnotated(uElement.javaPsi, ANT_POST_CONSTRUCT, 0)) {
            result.add(markerFor(element, element))
        } else if (uElement is UField && uElement.javaPsi is PsiField && AnnotationUtil.isAnnotated(uElement.javaPsi as PsiField, ANTS_INJECT, 0)) {
            result.add(markerFor(element, element))
        }
    }

    private fun markerFor(element: PsiElement, targets: PsiElement): RelatedItemLineMarkerInfo<PsiElement> {
        return NavigationGutterIconBuilder
            .create(Icons.GHILL)
            .setAlignment(GutterIconRenderer.Alignment.LEFT)
            .setTargets(targets)
            .createLineMarkerInfo(element)
    }
}
