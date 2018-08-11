package com.example.bill.testlib;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Created by Bill on 2018/8/7.
 */

@AutoService(Processor.class)
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


                MethodSpec methodSpec = MethodSpec.methodBuilder("print")
                        .addModifiers(Modifier.PUBLIC)
                        .returns(void.class)
                        .addStatement("$T.out.println($S)", System.class, "Hello")
                        .build();
                TypeSpec typeSpec = TypeSpec.classBuilder(clsName)
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(methodSpec)
                        .build();
                JavaFile javaFile = JavaFile.builder(pkgName, typeSpec)
                        .build();
                try {
                    javaFile.writeTo(filer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return true;
    }

    private String getPackageName(TypeElement type) {
        return elementsUtils.getPackageOf(type).getQualifiedName().toString();
    }


}
