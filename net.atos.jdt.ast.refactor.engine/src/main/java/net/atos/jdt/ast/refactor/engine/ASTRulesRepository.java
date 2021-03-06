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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.ICompilationUnit;

/**
 * AST Rule Repository. Contains rules and information about context & markers.
 * 
 * @author mvanbesien
 * @since 1.0
 * 
 */
public class ASTRulesRepository {

	/**
	 * Rule Repository ID
	 */
	private final String id;

	/**
	 * List of read rules.
	 */
	private final Set<ASTRuleDescriptor> rules = new HashSet<ASTRuleDescriptor>();

	/**
	 * Creates new repository with ID & Associated Marker
	 * 
	 * @param id
	 */
	public ASTRulesRepository(final String id) {
		this.id = id;
	}

	/**
	 * Repository's ID
	 * 
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Returns the rules within this repository, that match the compilation unit
	 * passed as parameter (filtered by context)
	 * 
	 * @param compilationUnit
	 * @return
	 */
	public Set<ASTRuleDescriptor> getRules(final ICompilationUnit compilationUnit) {
		final Set<ASTRuleDescriptor> rules = new HashSet<ASTRuleDescriptor>();
		for (final ASTRuleDescriptor rule : this.rules) {
			boolean ruleEnabled = rule.isMandatory() || ASTRulesPreferences.isEnabled(rule);
			if (ruleEnabled) {
				rules.add(rule);
			}
		}
		return rules;
	}

	/**
	 * Returns all the rules located within this repository. (Unfiltered at all)
	 * 
	 * @return
	 */
	public Set<ASTRuleDescriptor> getRules() {
		return new HashSet<ASTRuleDescriptor>(this.rules);
	}

	/**
	 * Sets all rules provided as rules from repository.
	 * 
	 * @param ruleDescriptors
	 */
	public void registerRules(final Set<ASTRuleDescriptor> ruleDescriptors) {
		this.rules.clear();
		for (final ASTRuleDescriptor rule : ruleDescriptors) {
			this.rules.add(rule);
			rule.setRuleRepository(this);
		}

	}

	/**
	 * Set provided rule as rule from repository.
	 * 
	 * @param rule
	 */
	public void registerRule(final ASTRuleDescriptor rule) {
		this.rules.add(rule);
		rule.setRuleRepository(this);
	}

}
