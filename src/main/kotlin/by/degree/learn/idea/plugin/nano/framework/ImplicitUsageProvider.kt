package by.degree.learn.idea.plugin.nano.framework

import com.intellij.codeInsight.AnnotationUtil
import com.intellij.codeInsight.daemon.ImplicitUsageProvider
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiField
import com.intellij.psi.PsiMethod

class ImplicitUsageProvider : ImplicitUsageProvider {
    override fun isImplicitWrite(element: PsiElement): Boolean {
        if (element is PsiField) {
            return AnnotationUtil.isAnnotated(element, ANTS_INJECT, 0)
        }
        return false
    }

    override fun isImplicitRead(element: PsiElement): Boolean = false

    override fun isImplicitUsage(element: PsiElement): Boolean {
        if (element is PsiMethod) {
            return AnnotationUtil.isAnnotated(element, ANT_POST_CONSTRUCT, 0)
        } else if (element is PsiClass) {
            return FrameworkHelper.isPartOfFramework(element) || FrameworkHelper.isComponent(element)
        }

        return false
    }

}
