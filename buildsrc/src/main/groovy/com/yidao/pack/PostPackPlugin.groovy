package com.yidao.pack

import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

import java.text.SimpleDateFormat;

class PostPackPlugin implements Plugin<Project> {

    private static final String EXTENSION_NAME = "postpack";

    def outputPath = "\\outputs"
    def apkDir = outputPath + "\\apk\\";
    def mappingName = "mapping.txt";
    def mappingPath = outputPath + "\\mapping\\release\\" + mappingName;
    def encryptFolder = "..\\encrypt\\jiagu"

    FileFilter ff = new FileFilter() {
        @Override
        boolean accept(File pathname) {
            if (pathname.isDirectory()) {
                return false;
            }
            String name = pathname.getName();
            if (name.endsWith("release.apk")) {
                return true;
            }
            return false;
        }
    }

    @Override
    void apply(Project project) {
        if (project.getPlugins().hasPlugin(AppPlugin)) {
            println "apply pack"
            project.extensions.create(EXTENSION_NAME, PostPackExtension);
            applyTask(project);
        }
    }

    private void applyTask(Project project) {
        project.afterEvaluate {

            PostPackExtension pe = PostPackExtension.getConfig(project);
            if (!pe.enable) {
                println "postpack disabled"
                return;
            }

            def taskPreBuild = project.tasks.findByName("preBuild");
            def taskClean = project.tasks.findByName("clean");
            def packagePath = "..\\..\\packages\\" + project.getName();

            def taskBuild = project.tasks.findByName("build");
            def processPackageFolder = "processPackageFolderRelease"
            project.task(processPackageFolder) << {
                println "task processPackageFolder begin"
                File file = new File(packagePath);
                if (file.exists() && file.isDirectory()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
                    String dateString = formatter.format(System.currentTimeMillis());
                    String newPath = packagePath + "-" + dateString;
                    File newFile = new File(newPath);
                    file.renameTo(newFile);
                }
            }

            def replaceKey = "replaceKey"
            project.task(replaceKey) << {
                println "replace koala key"
                def path = project.getRootDir().getAbsolutePath() + "/multichannel/build.gradle"
                println path
                File encrpty = new File(path)
            }

            def pack = "packBegin"
            project.task(pack) << {
                println "task pack begin"
            }

            def taskProcessPackageFolder = project.tasks[processPackageFolder]
            def taskPackBegin = project.tasks[pack]
            def taskReplace = project.tasks[replaceKey]
            if (taskPreBuild != null
                    && taskBuild != null
                    && taskProcessPackageFolder != null) {
                if (pe.supportClean && taskClean != null) {
                    taskReplace.dependsOn taskClean
                    taskPreBuild.dependsOn taskReplace
                }
                taskProcessPackageFolder.dependsOn taskBuild
                taskPackBegin.dependsOn taskProcessPackageFolder
            }

        }
    }

}