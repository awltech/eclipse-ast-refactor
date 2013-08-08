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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.atos.jdt.ast.refactor.engine.internal.RefactorEngineMessages;
import net.atos.jdt.ast.refactor.engine.internal.extpt.ASTRulesExtensionPoint;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;

/**
 * Process that execute validation rules on ICompilationUnits
 * 
 * @author mvanbesien
 * @since 1.0
 */
public class ASTRefactorEngine {
	/**
	 * List of Compilation Units to be managed by this process
	 */
	private Collection<ICompilationUnit> compilationUnits = new ArrayList<ICompilationUnit>();;

	/**
	 * Valid Repositories for this engine
	 */
	private final String[] validRepositories;

	/**
	 * Rules Source, used to retrieve the rules during execution
	 */
	private IASTRulesDataSource dataSource;

	/**
	 * Creates new Validation Engine for Compilation Units as from parameters
	 * 
	 * @param compilationUnits
	 * @param validRepositories
	 */
	public ASTRefactorEngine(IASTRulesDataSource dataSource, final Collection<ICompilationUnit> compilationUnits,
			final String... validRepositories) {
		this.compilationUnits.clear();
		this.compilationUnits.addAll(compilationUnits);
		this.validRepositories = validRepositories;
		if (dataSource != null)
			this.dataSource = dataSource;
	}

	/**
	 * Creates new Validation Engine for Compilation Units as from parameters
	 * 
	 * @param compilationUnits
	 * @param validRepositories
	 */
	public ASTRefactorEngine(final Collection<ICompilationUnit> compilationUnits, final String... validRepositories) {
		this.compilationUnits.clear();
		this.compilationUnits.addAll(compilationUnits);
		this.validRepositories = validRepositories;
		this.dataSource = ASTRulesExtensionPoint.getInstance();
	}

	/**
	 * Runs the validation
	 * 
	 * @param monitor
	 * @throws CoreException
	 */
	public void execute(final IProgressMonitor monitor) throws CoreException {
		for (final ICompilationUnit compilationUnit : this.compilationUnits) {
			this.execute(compilationUnit, monitor);
		}
	}

	/**
	 * Runs the validation on one specific compilation unit
	 * 
	 * @param compilationUnit
	 * @param monitor
	 * @throws CoreException
	 */
	private void execute(final ICompilationUnit compilationUnit, final IProgressMonitor monitor) throws CoreException {

		final List<ASTRulesRepository> repositories = this.dataSource.getRepositories(this.validRepositories);
		for (final ASTRulesRepository ruleRepository : repositories) {
			for (final ASTRuleDescriptor ruleDescriptor : ruleRepository.getRules(compilationUnit)) {
				monitor.subTask(RefactorEngineMessages.VALIDATING_CU.value(compilationUnit.getElementName(),
						ruleDescriptor.getDescription()));
				ruleDescriptor.getRule().run(compilationUnit);
			}
		}
	}
}
