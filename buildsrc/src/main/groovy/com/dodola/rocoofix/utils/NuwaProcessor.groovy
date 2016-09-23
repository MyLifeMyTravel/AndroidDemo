/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.dodola.rocoofix.utils

import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.Project
import org.objectweb.asm.*

import java.lang.reflect.Method
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

/**
 * Created by jixin.jia on 15/11/10.
 */
class NuwaProcessor {

//
//    public void processClasses(File inputFile,  HashSet<String> includePackage, HashSet<String> excludeClass, String dirName, Map hashMap, File patchDir) {
//        def path = inputFile.absolutePath
//        println("====inputFile-------->" + path)
//        if (path.endsWith(".class") && !path.contains("/R\$") && !path.endsWith("/R.class") && !path.endsWith("/BuildConfig.class")) {
//            if (NuwaSetUtils.isIncluded(path, includePackage)) {
//                if (!NuwaSetUtils.isExcluded(path, excludeClass)) {
//                    def bytes = NuwaProcessor.processClass(inputFile)
//                    path = path.split("${dirName}/")[1]
//                    def hash = DigestUtils.shaHex(bytes)
//                    hashFile.append(RocooUtils.format(path, hash))
//
//                    if (RocooUtils.notSame(hashMap, path, hash)) {
//                        def file = new File("${patchDir}/${path}")
//                        file.getParentFile().mkdirs()
//                        if (!file.exists()) {
//                            file.createNewFile()
//                        }
//                        FileUtils.writeByteArrayToFile(file, bytes)
//                    }
//                }
//            }
//        }
//    }


    public
    static processJar(Project project, File hashFile, File jarFile, File patchDir, Map map, HashSet<String> includePackage, HashSet<String> excludeClass) {
        if (jarFile) {
            def optJar = new File(jarFile.getParent(), jarFile.name + ".opt")

            def file = new JarFile(jarFile);
            Enumeration enumeration = file.entries();
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(optJar));

            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement();
                String entryName = jarEntry.getName();
                ZipEntry zipEntry = new ZipEntry(entryName)

                entryName = RocooUtils.transformSlash(entryName);

                InputStream inputStream = file.getInputStream(jarEntry);
                jarOutputStream.putNextEntry(zipEntry);

                if (shouldProcessClassInJar(entryName, includePackage, excludeClass)) {
                    def bytes = referHackWhenInit(project, inputStream);
                    jarOutputStream.write(bytes);

                    def hash = DigestUtils.shaHex(bytes)
                    println("file hash:--------------->" + entryName + ":" + hash)
                    hashFile.append(RocooUtils.format(entryName, hash))

                    if (RocooUtils.notSame(map, entryName, hash)) {
                        println("file notSame:--------------->" + entryName)
                        project.logger.error "nima"

                        def entryFile = new File("${patchDir}/${entryName}")
                        entryFile.getParentFile().mkdirs()
                        if (!entryFile.exists()) {
                            entryFile.createNewFile()
                        }
                        FileUtils.writeByteArrayToFile(entryFile, bytes)
                    }
                } else {
                    jarOutputStream.write(IOUtils.toByteArray(inputStream));
                }
                jarOutputStream.closeEntry();
            }
            jarOutputStream.close();
            file.close();

            if (jarFile.exists()) {
                jarFile.delete()
            }
            optJar.renameTo(jarFile)
        }

    }

    //refer hack class when object init
    private static byte[] referHackWhenInit(Project project, InputStream inputStream) {
        ClassReader cr = new ClassReader(inputStream);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);

        ClassVisitor cv = new ClassVisitor(Opcodes.ASM4, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc,
                                             String signature, String[] exceptions) {

                MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                mv = new MethodVisitor(Opcodes.ASM4, mv) {
                    @Override
                    void visitInsn(int opcode) {
                        if ("<init>".equals(name) && opcode == Opcodes.RETURN) {
//                            super.visitLdcInsn(Type.getType("Lcom/yidao/secondex/Example;"));
//                            super.visitLdcInsn(Type.getType("Lcom/test/rocootest/Test;"));
//                            super.visitLdcInsn(Type.getType(project.class));
//                            super.visitLdcInsn(project.getName());
                            super.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out",
                                    "Ljava/io/PrintStream;");
                            super.visitLdcInsn(Type.getType("Lcom/yidao/secondex/Example;"));
                            // invokes the 'println' method (defined in the PrintStream class)
                            super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
                                    "(Ljava/lang/Object;)V");
                        }
                        super.visitInsn(opcode);
                    }
                }
                return mv;
            }

        };
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    public static boolean shouldProcessPreDexJar(String path) {
        return path.endsWith("classes.jar") &&
                !path.contains("com.android.support") &&
                !path.contains("/android/m2repository");
    }

    private
    static boolean shouldProcessClassInJar(String entryName, HashSet<String> includePackage, HashSet<String> excludeClass) {
        return entryName.endsWith(".class") &&
                !entryName.startsWith("com/dodola/rocoofix/") &&
                !entryName.startsWith("com/lody/legend/") &&
                NuwaSetUtils.isIncluded(entryName, includePackage) &&
                !NuwaSetUtils.isExcluded(entryName, excludeClass) &&
                !entryName.contains("android/support/")
    }

    public static byte[] processClass(Project project, File file) {
        def optClass = new File(file.getParent(), file.name + ".opt")

        FileInputStream inputStream = new FileInputStream(file);
        FileOutputStream outputStream = new FileOutputStream(optClass)

        def bytes = referHackWhenInit(project, inputStream);
        outputStream.write(bytes)
        inputStream.close()
        outputStream.close()
        if (file.exists()) {
            file.delete()
        }
        optClass.renameTo(file)
        return bytes
    }
}
