/*
 *     Eclipse AST Validation, lite framework to validate java code
 *     
 *     Copyright (C) 2013 Atos Worldline or third-party contributors as
 *     indicated by the @author tags or express copyright attribution
 *     statements applied by the authors.
 *     
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License.
 *     
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *     Lesser General Public License for more details.
 *     
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package net.atos.jdt.ast.refactor.engine.internal;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Class containing messages for AST Validation runtime
 * 
 * @author mvanbesien
 * @since 1.0
 */
public enum RefactorEngineMessages {
	RETRIEVING_CU, VALIDATING_SOURCE, VALIDATING_CU, RULE_LOADING_EXCEPTION,CONTEXT_LOADING_EXCEPTION, EXECUTION_EXECPTION;

	/*
	 * ResourceBundle instance
	 */
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("RefactorEngineMessages");

	/*
	 * Returns value of the message
	 */
	public String value() {
		if (RefactorEngineMessages.resourceBundle == null || !RefactorEngineMessages.resourceBundle.containsKey(this.name())) {
			return "!!" + this.name() + "!!";
		}

		return RefactorEngineMessages.resourceBundle.getString(this.name());
	}

	/*
	 * Returns value of the formatted message
	 */
	public String value(final Object... args) {
		if (RefactorEngineMessages.resourceBundle == null || !RefactorEngineMessages.resourceBundle.containsKey(this.name())) {
			return "!!" + this.name() + "!!";
		}

		return MessageFormat.format(RefactorEngineMessages.resourceBundle.getString(this.name()), args);
	}

}
