package com.feifei.second

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.api.ApplicationVariant
import com.android.repository.impl.meta.Archive
import com.feifei.second.hostconfig.HostConfig
import com.feifei.second.transform.ReClassTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

public class SecondPlugin implements Plugin<Project>{

    void apply(Project project){
        System.out.println("==========")
        System.out.println("feifei  第二个内部用插件")
        System.out.println("==========")

        project.extensions.create("pluginExt",PluginExtension)
        project.pluginExt.extensions.create("nestExt", PluginNestExtension)
        project.task('customTask',type:CustomTask)

        def isApp = project.plugins.getPlugin(AppPlugin)

        if(isApp){
            def android =  project.extensions.getByType(AppExtension)
            android.registerTransform(new ReClassTransform(project))



            android.applicationVariants.all { variants->

                def variantData =  variants.variantData
                def scope = variantData.scope

                println "feifei current scope:"+scope

                //scope.getTaskName 的作用 就是结合当前scope 拼接人物名
                def taskName = scope.getTaskName("CreateHostConfig")
                def createTask = project.task(taskName)

                println "feifei CreateHostConfigTaskName:"+taskName

                //自定义task 增加action
                createTask.doLast {
                    HostConfig.createHostConfig(variants,project.pluginExt)
                }

                String generateBuildConfigTaskName = scope.getGenerateBuildConfigTask().name
                def generateBuildConfigTask = project.tasks.getByName(generateBuildConfigTaskName)
                println "feifei  generateBuildConfigTaskName:"+generateBuildConfigTaskName

                if(generateBuildConfigTask){
                    createTask.dependsOn generateBuildConfigTask
                    generateBuildConfigTask.finalizedBy(createTask)//执行完generateBuildConfigTask之后,执行createTask任务
                }
            }
        }
    }
}