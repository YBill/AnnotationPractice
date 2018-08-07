package com.example.bill.testlib;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by Bill on 2018/8/5.
 */

public class GenerateProcessor extends AbstractProcessor {

    private Messager messager; // 输入日志
    private Filer filer; // 生成文件
    private Elements elementsUtils; // Element工具

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(GenerateCode.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        elementsUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(GenerateCode.class);

        if (elements.iterator().hasNext()) {
            Element element = elements.iterator().next(); // 获取set集合的第一个元素
            if (ElementKind.CLASS == element.getKind()) {
                TypeElement typeElement = (TypeElement) element;
                final String pkgName = getPackageName(typeElement); // 获取包名
                messager.printMessage(Diagnostic.Kind.NOTE, "pkgName:" + pkgName); // 控制台输入的，没有实质意义

                final String clsName = typeElement.getSimpleName().toString() + "$GA"; // 获取简单类名

                String code = jointCode(pkgName, clsName);

                Writer writer = null;
                try {
                    JavaFileObject file = filer.createSourceFile(pkgName + "." + clsName);
                    writer = file.openWriter();
                    writer.write(code);

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (writer != null)
                            writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
        }

        return true;
    }

    /**
     * 拼接代码
     *
     * @param pkgName 包名
     * @param clsName 类名
     * @return  拼接效果下面这样
     *
      package pkgName;

      public class clsName {
          public void print() {
             System.out.println("Hello");
          }
      }
     */
    private String jointCode(String pkgName, String clsName) {
        StringBuilder builder = new StringBuilder();
        builder.append("package ");
        builder.append(pkgName);
        builder.append(";");
        builder.append("\n\n");
        builder.append("public class ");
        builder.append(clsName);
        builder.append(" {");
        builder.append("\n");
        builder.append("\tpublic void print() {");
        builder.append("\n");
        builder.append("\t\tSystem.out.println(\"Hello\");");
        builder.append("\n");
        builder.append("\t}");
        builder.append("\n");
        builder.append("}");

        return builder.toString();
    }

    private String getPackageName(TypeElement type) {
        return elementsUtils.getPackageOf(type).getQualifiedName().toString();
    }


}
