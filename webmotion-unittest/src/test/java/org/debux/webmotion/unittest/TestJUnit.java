/*
 * #%L
 * WebMotion unit test
 * $Id:$
 * $HeadURL:$
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
package org.debux.webmotion.unittest;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Verify you can launch a request for JUnit test.
 * 
 * @author julien
 */
@RunWith(JUnit4.class)
public class TestJUnit extends WebMotionJUnit {

    @Override
    protected String getWebappLocation() {
        return "src/test/resources/webapp";
    }
    
    @Test
    public void testRequest() throws IOException, URISyntaxException {
        String result = createRequest("/test").Get()
                .execute().returnContent().asString();
        
        assertTrue(result.contains("Index"));
    }
    
}
