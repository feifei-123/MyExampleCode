package com.feifei.second.transform
import com.android.build.api.transform.*
import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.utils.FileUtils
import com.feifei.second.codeinject.CodeInjects
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.Project
import org.gradle.internal.impldep.org.apache.ivy.util.FileUtil
import org.gradle.jvm.tasks.Jar
import com.android.build.gradle.internal.pipeline.TransformManager

import javax.xml.crypto.dsig.TransformException

public class ReClassTransform extends Transform{

    private Project mProject;

    public ReClassTransform(Project p){
        this.mProject = p;
    }

    //transform的名称
    /**
     * 最终运行的名字为 transformClassWith+getName()+For+{BuildType}+{ProductFlavor}
     * 如 transformClassWithXXXForDebug
     * @return
     */
    @Override
    String getName() {
        return "XXX"
    }

    /**
     * 需要处理的数据类型，有两种枚举类型
     * CLASSES和RESOURCES，CLASSES代表处理的java的class文件;RESOURCES代表要处理java的资源.
     * @return
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    /**
     * 指Transform要操作内容的范围，官方文档Scope有7种类型：
     * EXTERNAL_LIBRARIES   只有外部库
     * PROJECT              只有项目内容
     * PROJECT_LOCAL_DEPS   只有项目的本地依赖(本地jar)
     * PROVIDED_ONLY        只提供本地或远程依赖项
     * SUB_PROJECTS         只有子项目
     * SUB_PROJECTS_LOCAL_DEPS 只有子项目的本地依赖项(本地jar)。
     * TESTED_CODE          由当前变量(包括依赖项)测试的代码
     * @return
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }
//指明当前Transform是否支持增量编译
    @Override
    boolean isIncremental() {
        return false
    }

    /**
     * Transform中的核心方法，
     *
     * @param context 。
     * @param inputs  传过来的输入流, 其中有两种格式，一种是jar包格式一种是目录格式
     * @param referencedInputs
     * @param outputProvider  获取到输出目录，最后将修改的文件复制到输出目录，这一步必须做不然编译会报错
     * @param isInCremental
     * @throws IOException
     * @throws TransformException
     */
    @Override
    public void transform(Context context,
                          Collection<TransformInput> inputs,
            Collection<TransformInput> referencedInputs,
            TransformOutputProvider outputProvider,
            boolean isInCremental
    ) throws IOException, TransformException{

        welecome()

        inputs.each { TransformInput input->

            //遍历目录
            input.directoryInputs.each { DirectoryInput directoryInput ->

                println "direction = "+directoryInput.file.getAbsolutePath()

                CodeInjects.inject(directoryInput.file.absolutePath,mProject)
                //获取输出目录
                def dest = outputProvider.getContentLocation(directoryInput.name,directoryInput.contentTypes,directoryInput.scopes,Format.DIRECTORY)

                //对于目录中的class文件原样输出
                FileUtils.copyDirectory(directoryInput.file,dest)
            }

            //遍历jar文件,对jar不操作，但是要输出到out目录
            input.jarInputs.each { JarInput jarInput->

                // 将jar文件 重命名输出文件（同目录copyFile会冲突）
                def jarName = jarInput.name
                println "jar = "+jarInput.file.getAbsolutePath()

                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if(jarName.endsWith(".jar")){
                    jarName = jarName.substring(0,jarName.length()-4)
                }
                def dest = outputProvider.getContentLocation(jarName+md5Name,jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, dest)
            }

        }

        end()

    }


    def welecome(){
        println "----welcome to ReClassTransform"
    }

    def end(){
        println "----ReClassTransform end"
    }
}