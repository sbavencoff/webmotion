/*
 * #%L
 * WebMotion server
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 - 2015 Debux
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package org.debux.webmotion.server.parser;

import java.util.List;
import org.debux.webmotion.server.mapping.*;

/**
 * Visitor pattern to look into the mapping. Implements Visitor class to add 
 * behavior. The visitor method is call on each extensions and rules.
 * 
 * @author julien
 */
public class MappingVisit {
  
    /**
     * Use to add behaviour during run.
     */
    public static class Visitor {
        
        /**
         * Call on the mapping and each extension.
         * 
         * @param mapping current mapping
         */
        public void accept(Mapping mapping) {
        }
        
        /**
         * Call each rule in mapping.
         * 
         * @param mapping current mapping
         * @param rule current rule
         */
        public void accept(Mapping mapping, Rule rule) {
        }
        
        /**
         * Call each rule type of ActionRule in mapping.
         * 
         * @param mapping current mapping
         * @param actionRule current rule
         */
        public void accept(Mapping mapping, ActionRule actionRule) {
        }
        
        /**
         * Call each rule type of ErrorRule in mapping.
         * 
         * @param mapping current mapping
         * @param errorRule current rule
         */
        public void accept(Mapping mapping, ErrorRule errorRule) {
        }
        
        /**
         * Call each rule type of FilterRule in mapping.
         * 
         * @param mapping current mapping
         * @param filterRule current rule
         */
        public void accept(Mapping mapping, FilterRule filterRule) {
        }
        
        /**
         * Call each extension in mapping.
         * 
         * @param mapping current mapping
         * @param extension current extension
         */
        public void accept(Mapping mapping, Mapping extension) {
        }
    }
    
    /**
     * Implements the visit on mapping.
     * 
     * @param mapping current mapping
     * @param visitor current visitor implementation
     */
    public void visit(Mapping mapping, Visitor visitor) {
        visitor.accept(mapping);
        
        List<FilterRule> filterRules = mapping.getFilterRules();
        for (FilterRule filterRule : filterRules) {
            visitor.accept(mapping, (Rule) filterRule);
            visitor.accept(mapping, filterRule);
        }

        List<ActionRule> actionRules = mapping.getActionRules();
        for (ActionRule actionRule : actionRules) {
            visitor.accept(mapping, (Rule) actionRule);
            visitor.accept(mapping, actionRule);
        }

        List<ErrorRule> errorRules = mapping.getErrorRules();
        for (ErrorRule errorRule : errorRules) {
            visitor.accept(mapping, (Rule) errorRule);
            visitor.accept(mapping, errorRule);
        }

        List<Mapping> extensionsRules = mapping.getExtensionsRules();
        for (Mapping extension : extensionsRules) {
            visitor.accept(mapping, extension);
            visit(extension, visitor);
        }
    }
    
}
