package by.degree.learn.idea.plugin.solid.framework

import com.intellij.codeInsight.AnnotationUtil
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import org.apache.commons.lang3.AnnotationUtils
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.getUParentForIdentifier
import java.util.*

class SolidLineMarkerProvider : RelatedItemLineMarkerProvider() {
    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<PsiElement>>
    ) {
        if (element is PsiMethod && AnnotationUtil.isAnnotated(element, ANNOTATION_POST_CONSTRUCT, 0))
        {
            val marker = NavigationGutterIconBuilder
                .create(Icons.GHILL)
                .setAlignment(GutterIconRenderer.Alignment.LEFT)
                .setTargets(element)
                .createLineMarkerInfo(element)

            result.add(marker)
        }
    }
}
