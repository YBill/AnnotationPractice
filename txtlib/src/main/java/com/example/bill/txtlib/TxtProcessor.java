package com.example.bill.txtlib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;

/**
 * Created by Bill on 2018/8/1.
 */

@SupportedAnnotationTypes({"com.example.bill.txtlib.Txtization"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class TxtProcessor extends AbstractProcessor {

    // 注解辅助工具类
    private Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementUtils = processingEnvironment.getElementUtils();
    }

    /*@Override
    public Set<String> getSupportedAnnotationTypes() {
        // 此方法和类上面注解SupportedAnnotationTypes功能一样
        Set<String> set = new LinkedHashSet<>();
        set.add(Txtization.class.getCanonicalName());
        return set;
    }*/

    /*@Override
    public SourceVersion getSupportedSourceVersion() {
        // 此方法和类上面注解SupportedSourceVersion功能一样
        return SourceVersion.latestSupported();
    }*/

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Txtization.class);

        Map<String, List<VariableElement>> map = new HashMap<>();

        // 解析注解信息，并添加到map中
        collectInfo(elements, map);

        // 生成txt文件
        generateTxt(map);

        return true; // 拦截后续Processor的处理
    }

    // 解析收集注解信息
    private void collectInfo(Set<? extends Element> elements, Map<String, List<VariableElement>> map) {
        for (Element element : elements) {
            // kind代表注解的类型，是个枚举类，具体代表的意义可以看一下常量名称就知道了
            ElementKind kind = element.getKind();
            // 由于我们定义的注解Txtization表明了只可以修饰类和属性，所以下面就可以解析这两个就可以了
            if (kind == ElementKind.CLASS) { // 类
                TypeElement typeElement = (TypeElement) element; // TypeElement类元素
                List<? extends Element> allMembers = mElementUtils.getAllMembers(typeElement); // 获取所有类成员
                String qualifiedName = typeElement.getQualifiedName().toString(); // 返回此类型元素的完全限定名称(包名+类名)
                // ElementFilter.fieldsIn(allMembers) : 工具类将若有类成员转换为VariableElement类型
                map.put(qualifiedName, ElementFilter.fieldsIn(allMembers));
            } else if (kind == ElementKind.FIELD) { // 属性
                VariableElement variableElement = (VariableElement) element; // VariableElement字段
                TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement(); // 获取相关类
                String qualifiedName = typeElement.getQualifiedName().toString();
                List<VariableElement> variableElementList = map.get(qualifiedName);
                if (variableElementList == null) {
                    variableElementList = new ArrayList<>();
                    map.put(qualifiedName, variableElementList);
                }
                if (!variableElementList.contains(variableElement))
                    variableElementList.add(variableElement);
            }
        }
    }

    // 生成txt
    private void generateTxt(Map<String, List<VariableElement>> map) {
        File dir = new File("/Users/xxx/Desktop/json/"); // 生成到桌面json文件夹中
        if (!dir.exists()) {
            dir.mkdir();
        }

        for (String s : map.keySet()) {
            File file = new File(dir, s.replaceAll("\\.", "_") + ".txt"); // 文件名，.替换为_
            FileWriter fw = null;
            try {
                fw = new FileWriter(file);
                fw.append("class:").append("\"" + s + "\"").append(",\n"); // s类全限定名
                List<VariableElement> fields = map.get(s);
                for (int i = 0; i < fields.size(); i++) {
                    VariableElement field = fields.get(i);
                    // field.getSimpleName() 属性名   field.asType() 类型
                    fw.append(field.getSimpleName()).append(":").append("\"" + field.asType().toString() + "\"");
                    if (i < fields.size() - 1) {
                        fw.append(",");
                        fw.append("\n");
                    }
                }

                fw.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
