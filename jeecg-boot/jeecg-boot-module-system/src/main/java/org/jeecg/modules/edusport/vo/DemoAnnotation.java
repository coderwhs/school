package org.jeecg.modules.edusport.vo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.jeecgframework.poi.excel.annotation.Excel;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;


/**
 * @Title: 动态生成类（Class）、属性（Field）、注解（Annotation）的示例
 * @Author andypmp4u
 * @site 
 * @Version:V1.0.0
 */
public class DemoAnnotation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
	        ClassPool pool = ClassPool.getDefault(); 
	        CtClass ct = pool.get("org.jeecg.modules.edusport.vo.AthleteSelectionTestAthleteScoreDetailVo");
	        
	        // 2. 新增一个字段 private String indexCode001;
	        // 字段名为 indexCode001
	        CtField ctField = new CtField(pool.get("java.lang.String"), "indexCode001", ct);
	        // 访问级别是 private
	        ctField.setModifiers(Modifier.PRIVATE);
	        // 初始值是 ""
	        ct.addField(ctField, CtField.Initializer.constant(""));
	        
	        // 3. 生成 getter、setter 方法
	        ct.addMethod(CtNewMethod.setter("setIndexCode001", ctField));
	        ct.addMethod(CtNewMethod.getter("getIndexCode001", ctField));
	        
	        // 4. 添加字段的注解
	        FieldInfo fieldInfo = ctField.getFieldInfo();
	        ConstPool cp = fieldInfo.getConstPool();
	        Annotation annotation = new Annotation("org.jeecgframework.poi.excel.annotation.Excel", cp);
	        annotation.addMemberValue("name", new StringMemberValue("大腿长", cp));
	        AnnotationsAttribute attribute = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
	        attribute.setAnnotation(annotation);
	        fieldInfo.addAttribute(attribute);
	        
	        
	        // 2. 新增一个字段 private String indexCode001;
	        // 字段名为 indexCode001
	        CtField ctField2 = new CtField(pool.get("java.lang.String"), "indexCode002", ct);
	        // 访问级别是 private
	        ctField2.setModifiers(Modifier.PRIVATE);
	        // 初始值是 ""
	        ct.addField(ctField2, CtField.Initializer.constant(""));
	        
	        // 3. 生成 getter、setter 方法
	        ct.addMethod(CtNewMethod.setter("setIndexCode002", ctField2));
	        ct.addMethod(CtNewMethod.getter("getIndexCode002", ctField2));
	        
	        // 4. 添加字段的注解
	        FieldInfo fieldInfo2 = ctField2.getFieldInfo();
	        ConstPool cp2 = fieldInfo2.getConstPool();
	        Annotation annotation2 = new Annotation("org.jeecgframework.poi.excel.annotation.Excel", cp2);
	        annotation2.addMemberValue("name", new StringMemberValue("肺活量", cp2));
	        AnnotationsAttribute attribute2 = new AnnotationsAttribute(cp2, AnnotationsAttribute.visibleTag);
	        attribute2.setAnnotation(annotation2);
	        fieldInfo2.addAttribute(attribute2);
	        
	        // X.修改已有字段的注解
	        CtField ctFieldx = ct.getField("indexCode1");
	        FieldInfo fieldInfox = ctFieldx.getFieldInfo();
	        ConstPool cpx = fieldInfox.getConstPool();
	        Annotation annotationx = new Annotation("org.jeecgframework.poi.excel.annotation.Excel", cpx);
	        annotationx.addMemberValue("name", new StringMemberValue("xxx", cpx));
	        AnnotationsAttribute attributex = new AnnotationsAttribute(cpx, AnnotationsAttribute.visibleTag);
	        attributex.setAnnotation(annotationx);
	        fieldInfox.addAttribute(attributex);
	        
	        
	        Class<?> clazz = ct.toClass();
	        AthleteSelectionTestAthleteScoreDetailVo vo = (AthleteSelectionTestAthleteScoreDetailVo) clazz.newInstance();
	        Field[] fieldList = vo.getClass().getDeclaredFields();
	        for (Field field : fieldList) {
	        	Excel excel = (Excel) field.getAnnotation(org.jeecgframework.poi.excel.annotation.Excel.class);
		        System.out.println("excel.name: " + (null==excel ? "null" : excel.name()));
		        System.out.println("field.getName: " + field.getName());
	        }
	        Method[] methodList = vo.getClass().getDeclaredMethods();
	        for (Method method : methodList) {
	        	System.out.println("method.getName: " + method.getName());
	        }
	        
	        Method setIndexName = vo.getClass().getDeclaredMethod("setIndexName", java.lang.String.class);
	        setIndexName.invoke(vo, "ABC");
	        
	        Method setIndexCode1 = vo.getClass().getDeclaredMethod("setIndexCode1", java.lang.String.class);
	        setIndexCode1.invoke(vo, "123");
	        

	        AthleteSelectionTestCoachVo testCoachVo = new AthleteSelectionTestCoachVo();
	        ArrayList<AthleteSelectionTestAthleteScoreDetailVo> testAthleteScoreDetailVoList = new ArrayList<>();
	        testAthleteScoreDetailVoList.add(vo);
	        testCoachVo.setTestAthleteScoreDetailList(testAthleteScoreDetailVoList);
	        
	        System.out.println("vo: " + vo);
	        System.out.println("testCoachVo.getTestAthleteScoreDetailList(): " + testCoachVo.getTestAthleteScoreDetailList());
	        
		} catch (SecurityException | NotFoundException | CannotCompileException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
