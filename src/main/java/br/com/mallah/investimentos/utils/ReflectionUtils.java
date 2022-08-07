package br.com.mallah.investimentos.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import br.com.mallah.investimentos.sheet.annotation.Column;
import br.com.mallah.investimentos.sheet.annotation.Sheet;

public class ReflectionUtils {

	public static Optional<Class<?>> getSheetAnnotatedClass(String sheetName) {
		Reflections reflections = new Reflections("br.com.mallah.investimentos.sheet.model", Scanners.TypesAnnotated);
		return reflections.getTypesAnnotatedWith(Sheet.class).stream()
				.filter(c -> {
					Sheet annotation = c.getAnnotation(Sheet.class);
					return sheetName.equals(annotation.name());
				})
				.findFirst();
	}
	
	public static <T> Optional<T> getNewInstance(Class<T> clazz) {
		Constructor<?> constructor;
		try {
			constructor = clazz.getConstructor();
			return Optional.of((T) constructor.newInstance());
		} catch (NoSuchMethodException | SecurityException | InstantiationException | 
				IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return Optional.empty();
		}
	}
	
	public static Optional<Field> getColumnAnnotatedField(Class<?> clazz, String annotation) {
		List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
		return fields.stream()
			.filter(f -> {
				Column columnAnnotation = f.getAnnotation(Column.class);
				if (columnAnnotation == null) 
					return false;
				return annotation.equals(columnAnnotation.name());
			})
			.findFirst();
	}
	
	public static void setValue(Field field, Object instance, Object value) {
		boolean canAccess = field.canAccess(instance);
		field.setAccessible(true);
		try {
			field.set(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalStateException("Erro setando campo '"+field.getName()+"' da classe '"+instance.getClass().getName()+"'", e);
		}
		field.setAccessible(canAccess);
	}
	
	public static Class<?> getReturnType(Field field) {
		return field.getType();
	}
	
	public static <T> Set<Class<? extends T>> getSubclasses(Class<T> clazz) {
		Reflections reflections = new Reflections("br.com.mallah.investimentos", Scanners.SubTypes);
		return reflections.getSubTypesOf(clazz);
	}
	
}
