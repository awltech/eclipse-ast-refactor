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
package net.atos.jdt.ast.refactor.engine;

import java.util.List;


/**
 * Interface that implementations that produce rules have to implement.
 * 
 * @author mvanbesien
 * 
 */
public interface IASTRulesDataSource {

	/**
	 * Return the list of repositories, filtered by IDs. If none is provided,
	 * returns all repositories.
	 * 
	 * @param repositories
	 * @return
	 */
	public List<ASTRulesRepository> getRepositories(final String... repositories);

}
