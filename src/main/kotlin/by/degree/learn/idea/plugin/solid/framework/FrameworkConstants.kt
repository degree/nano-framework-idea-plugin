package by.degree.learn.idea.plugin.solid.framework

const val FRAMEWORK_PACKAGE = "by.degree.learn.solid.framework."
const val FRAMEWORK_CLASS = FRAMEWORK_PACKAGE + "Application"

const val ANT_COMPONENT = FRAMEWORK_PACKAGE + "Component"
const val ANT_PRIMARY = FRAMEWORK_PACKAGE + "Primary"
const val ANT_SINGLETON = FRAMEWORK_PACKAGE + "Singleton"
const val ANT_POST_CONSTRUCT = FRAMEWORK_PACKAGE + "PostConstruct"
const val ANT_INJECT_PROPERTY = FRAMEWORK_PACKAGE + "InjectProperty"
const val ANT_INJECT = FRAMEWORK_PACKAGE + "Inject"

val ANTS_COMPONENT = listOf(ANT_COMPONENT, ANT_PRIMARY, ANT_SINGLETON)
val ANTS_INJECT = listOf(ANT_INJECT, ANT_INJECT_PROPERTY)

