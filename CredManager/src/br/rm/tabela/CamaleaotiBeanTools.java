/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rm.tabela;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

/**
 *
 * @author Wagner
 */
public class CamaleaotiBeanTools {

    public static String qualificarNomeDoMetodoGET(Field atributo) {
    	return "get" + atributo.getName().substring(0, 1).toUpperCase() + atributo.getName().substring(1);
        /*if ((atributo.getType() == Boolean.class) || (atributo.getType().toString().equals("boolean"))) {
            return "is" + atributo.getName().substring(0, 1).toUpperCase() + atributo.getName().substring(1);
        } else {
            return "get" + atributo.getName().substring(0, 1).toUpperCase() + atributo.getName().substring(1);
        }*/
    }

    public static String qualificarNomeDoMetodoSET(Field atributo) {
        return "set" + atributo.getName().substring(0, 1).toUpperCase() + atributo.getName().substring(1);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setValorNoAtributo(Object bean, String atributo, Object valor) throws NoSuchFieldException,
            NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        String[] acesso = atributo.split("[.]");

        Object objDeAcesso = bean;

        for (int i = 0; i < acesso.length; i++) {
            if(objDeAcesso != null){
                Class<?> classeDoAcesso = objDeAcesso.getClass();

                if (i < acesso.length - 1) {
                    try
                    {
                        objDeAcesso = classeDoAcesso.getMethod(qualificarNomeDoMetodoGET(classeDoAcesso.getDeclaredField(acesso[i])), new Class[]{}).invoke(objDeAcesso, new Object[]{});
                    }
                    catch (NoSuchFieldException ex)
                    {
                        objDeAcesso = classeDoAcesso.getMethod(qualificarNomeDoMetodoGET(classeDoAcesso.getSuperclass().getDeclaredField(acesso[i])), new Class[]{}).invoke(objDeAcesso, new Object[]{});
                    }
                }
                else
                {
                    if ((valor instanceof ArrayList) && (classeDoAcesso.getDeclaredField(acesso[i]).getType().equals(Set.class)))
                    {
                        try
                        {
                            classeDoAcesso.getMethod(qualificarNomeDoMetodoSET(classeDoAcesso.getDeclaredField(acesso[i])), new Class[]{Set.class}).invoke(objDeAcesso, new Object[]{new HashSet((ArrayList) valor)});
                        }
                        catch (NoSuchFieldException ex)
                        {
                            objDeAcesso = classeDoAcesso.getMethod(qualificarNomeDoMetodoGET(classeDoAcesso.getSuperclass().getDeclaredField(acesso[i])), new Class[]{}).invoke(objDeAcesso, new Object[]{});
                        }
                    }
                    else
                    {
                        try
                        {
                            classeDoAcesso.getMethod(qualificarNomeDoMetodoSET(classeDoAcesso.getDeclaredField(acesso[i])), new Class[]{classeDoAcesso.getDeclaredField(acesso[i]).getType()}).invoke(objDeAcesso, new Object[]{valor});
                        }
                        catch(java.lang.NoSuchFieldException nsfe)
                        {
                            //nao encontrou atributo na classe principal procura na superclasse
                            Class<?> superClasse = classeDoAcesso.getClass().getSuperclass();

                            if(!superClasse.getSimpleName().equalsIgnoreCase("Object"))
                                classeDoAcesso.getMethod(qualificarNomeDoMetodoSET(classeDoAcesso.getDeclaredField(acesso[i])), new Class[]{classeDoAcesso.getDeclaredField(acesso[i]).getType()}).invoke(objDeAcesso, new Object[]{valor});
                        }
                    }
                }
            }
        }
    }

    public static Object obterValorDoAtributo(Object bean, String atributo) throws NoSuchFieldException,
            NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        return CamaleaotiBeanTools.obterObjetoDeAcesso(bean, atributo);
    }

    public static Class<?> obterClasseDoAtributo(Object bean, String atributo) throws NoSuchFieldException,
            NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Object objDeAcesso = CamaleaotiBeanTools.obterObjetoDeAcesso(bean, atributo);

        if (objDeAcesso != null) {
            return objDeAcesso.getClass();
        } else {
            return null;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setAtributoNoComponente(Object bean, String atributo, Object componente, String metodoDeAtribuicao, Class<?> classeDoArgumento) throws NoSuchFieldException,
            NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Class<?> classeDoComponente = componente.getClass();

        Object objDeAcesso = CamaleaotiBeanTools.obterObjetoDeAcesso(bean, atributo);

        if (objDeAcesso != null) {
            if (objDeAcesso != null) {
                //if (componente instanceof JBeanTable)
                if (objDeAcesso instanceof Collection) {
                    classeDoComponente.getMethod(metodoDeAtribuicao, new Class[]{classeDoArgumento}).invoke(componente, new Object[]{new ArrayList((Collection) objDeAcesso)});
                } else {
                    classeDoComponente.getMethod(metodoDeAtribuicao, new Class[]{classeDoArgumento}).invoke(componente, new Object[]{objDeAcesso});
                }
            }
        }
    }

    private static Object obterObjetoDeAcesso(Object bean, String atributo) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String[] acesso = atributo.split("[.]");

        Object objDeAcesso = bean;

        for (int i = 0; i < acesso.length; i++) {
            if (objDeAcesso != null) {
                Class<?> classeDoAcesso = objDeAcesso.getClass();

                try{
                    //System.out.println(classeDoAcesso.getDeclaredField(acesso[i]));
                    objDeAcesso = classeDoAcesso.getMethod(qualificarNomeDoMetodoGET(classeDoAcesso.getDeclaredField(acesso[i])), new Class[]{}).invoke(objDeAcesso, new Object[]{});
                }catch(java.lang.NoSuchFieldException nsfe){
                    //nao encontrou atributo na classe principal procura na superclasse
                    Class<?> superClasse = objDeAcesso.getClass().getSuperclass();

                    if(!superClasse.getSimpleName().equalsIgnoreCase("Object"))
                        objDeAcesso = superClasse.getMethod(qualificarNomeDoMetodoGET(superClasse.getDeclaredField(acesso[i])), new Class[]{}).invoke(objDeAcesso, new Object[]{});
                }
            }
        }

        return objDeAcesso;
    }
}
