package com.kk.betting.client.ejb;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EJBProvider {

	public static final String BETTING_APPLICATION_NAME = "betting-application";

	@SuppressWarnings("unchecked")
	public static <R, T> R lookupRemoteBean(Class<R> remoteInterfaceName, Class<T> beanClassName, String appName,
			String moduleName) throws NamingException {
		Properties jndiProps = new Properties();
		jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		Context context = new InitialContext(jndiProps);

		String beanName = beanClassName.getSimpleName();
		String viewClassName = remoteInterfaceName.getName();
		String lookupUrl = "ejb:" + appName + "/" + moduleName + "/" + beanName + "!" + viewClassName;
		return (R) context.lookup(lookupUrl);
	}

	public static <R, T> R lookupRemoteBean(Class<R> remoteInterfaceName, Class<T> beanClassName, String appName)
			throws NamingException {
		return lookupRemoteBean(remoteInterfaceName, beanClassName, appName, "");
	}

	public static <R, T> R lookupRemoteBean(Class<R> remoteInterfaceName, Class<T> beanClassName)
			throws NamingException {
		return lookupRemoteBean(remoteInterfaceName, beanClassName, "", "");
	}

}
