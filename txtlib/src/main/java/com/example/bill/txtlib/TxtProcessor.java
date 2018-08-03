package com.example.bill.txtlib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

    private Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return super.getSupportedAnnotationTypes();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Txtization.class);

        Map<String, List<VariableElement>> map = new HashMap<>();

        collectInfo(elements, map);
        generateCode(map);

        return true;
    }

    private void collectInfo(Set<? extends Element> elements, Map<String, List<VariableElement>> map) {
        for (Element element : elements) {
            ElementKind kind = element.getKind();
            if (kind == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;
                List<? extends Element> allMembers = mElementUtils.getAllMembers(typeElement);
                String qualifiedName = typeElement.getQualifiedName().toString();
                map.put(qualifiedName, ElementFilter.fieldsIn(allMembers));
            } else if (kind == ElementKind.FIELD) {
                VariableElement variableElement = (VariableElement) element;
                TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
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

    private void generateCode(Map<String, List<VariableElement>> map) {
        File dir = new File("/Users/yuanweibiao/Desktop/json/");
        if (!dir.exists()) {
            dir.mkdir();
        }

        for (String s : map.keySet()) {
            File file = new File(dir, s.replaceAll("\\.", "_") + ".txt");
            FileWriter fw = null;
            try {
                fw = new FileWriter(file);
                fw.append("class:").append("\"" + s + "\"").append(",\n");
                List<VariableElement> fields = map.get(s);
                for (int i = 0; i < fields.size(); i++) {
                    VariableElement field = fields.get(i);
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
