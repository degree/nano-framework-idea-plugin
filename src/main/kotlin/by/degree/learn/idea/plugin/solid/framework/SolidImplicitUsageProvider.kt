package by.degree.learn.idea.plugin.solid.framework

import com.intellij.codeInsight.AnnotationUtil
import com.intellij.codeInsight.daemon.ImplicitUsageProvider
import com.intellij.psi.*

const val ANNOTATION_FRAMEWORK_PACKAGE = "by.degree.learn.framework."
const val ANNOTATION_POST_CONSTRUCT = "by.degree.learn.framework.PostConstruct"
const val ANNOTATION_INJECT_PROPERTY = "by.degree.learn.framework.InjectProperty"
const val ANNOTATION_INJECT = "by.degree.learn.framework.Inject"
val ANNOTATIONS_INJECT = listOf(ANNOTATION_INJECT, ANNOTATION_INJECT_PROPERTY)


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
