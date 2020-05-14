package by.degree.learn.idea.plugin.solid.framework

import com.intellij.codeInsight.intention.AddAnnotationFix
import com.intellij.codeInspection.*
import com.intellij.psi.PsiField

class SolidInspectionTool : AbstractBaseJavaLocalInspectionTool() {
    override fun checkField(
        field: PsiField,
        manager: InspectionManager,
        isOnTheFly: Boolean
    ): Array<ProblemDescriptor>? {

        val problemsHolder = ProblemsHolder(manager, field.containingFile, isOnTheFly)

        check(field, ANT_INJECT, problemsHolder)
        check(field, ANT_INJECT_PROPERTY, problemsHolder)

        return problemsHolder.resultsArray
    }

    private fun check(
        field: PsiField,
        annotationName: String,
        problemsHolder: ProblemsHolder
    ) {
        val annotation = field.getAnnotation(annotationName)
        if (annotation != null && !FrameworkHelper.isComponent(field.containingClass)) {
            problemsHolder.registerProblem(
                annotation,
                "@Component annotation missing",
                ProblemHighlightType.WARNING,
                AddAnnotationFix(ANT_COMPONENT, field.containingClass!!)
            )
        }
    }
}
