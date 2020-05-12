package by.degree.learn.idea.plugin.solid.framework

import com.intellij.codeInsight.AnnotationUtil
import com.intellij.codeInsight.daemon.ImplicitUsageProvider
import com.intellij.psi.*

class SolidImplicitUsageProvider : ImplicitUsageProvider {
    override fun isImplicitWrite(element: PsiElement): Boolean {
        if (element is PsiField) {
            return AnnotationUtil.isAnnotated(element, ANNOTATIONS_INJECT, 0)
        }
        return false
    }

    override fun isImplicitRead(element: PsiElement): Boolean = false

    override fun isImplicitUsage(element: PsiElement): Boolean {
        if (element is PsiMethod) {
            return AnnotationUtil.isAnnotated(element, ANNOTATION_POST_CONSTRUCT, 0)
        } else if (element is PsiClass) {
            return element.interfaces.any { psiClass ->  psiClass.qualifiedName?.startsWith(ANNOTATION_FRAMEWORK_PACKAGE) ?: false }
        }

        return false
    }
}
