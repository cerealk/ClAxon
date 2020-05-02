package org.xaos.intellij.axon.plugin

import com.intellij.framework.FrameworkTypeEx
import com.intellij.framework.addSupport.FrameworkSupportInModuleConfigurable
import com.intellij.framework.addSupport.FrameworkSupportInModuleProvider
import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ModifiableModelsProvider
import com.intellij.openapi.roots.ModifiableRootModel
import javax.swing.Icon
import javax.swing.JCheckBox
import javax.swing.JComponent


const val FRAMEWORK_ID = "org.xaos.intellij.axon.plugin.AxonFramework"
class AxonFramework: FrameworkTypeEx(FRAMEWORK_ID) {
    override fun getIcon(): Icon {
        TODO()
    }

    override fun getPresentableName(): String {
        return "Axon"
    }

    override fun createProvider(): FrameworkSupportInModuleProvider {
        return object : FrameworkSupportInModuleProvider() {
            
            override fun getFrameworkType(): FrameworkTypeEx {
                return this@AxonFramework
            }

            override fun isEnabledForModuleType(moduleType: ModuleType<*>): Boolean {
                return true
            }

            override fun createConfigurable( model: FrameworkSupportModel): FrameworkSupportInModuleConfigurable {
                return object : FrameworkSupportInModuleConfigurable() {
                    override fun addSupport(
                        module: Module,
                        rootModel: ModifiableRootModel,
                        modifiableModelsProvider: ModifiableModelsProvider
                    ) {
                        // This is the place to set up a library, generate a specific file, etc
                        // and actually add framework support to a module.

                        //Add support in gradle or maven for the axon dependency!
                    }

                    override fun createComponent(): JComponent? {
                        return JCheckBox("SDK Extra Option")
                    }
                }
            }


        }
    }
}