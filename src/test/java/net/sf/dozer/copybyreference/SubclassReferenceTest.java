/*
 * Copyright 2005-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.dozer.copybyreference;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import net.sf.dozer.DozerBeanMapper;
import net.sf.dozer.Mapper;

import org.junit.Before;
import org.junit.Test;

public class SubclassReferenceTest  {
  Mapper mapper;
  TestA testA;
  TestB testB;

  @Before
  public void setUp() {
    mapper = new DozerBeanMapper(Arrays.asList(new String[] { getMappingFile() }));
    testA = new TestA();
    testA.setOne("one");
    testA.setOneA("oneA");
    testB = null;
  }

  protected String getMappingFile() {
    return "reference-mapping.xml";
  }

  @Test
  public void testBase() {
    testB = mapper.map(testA, TestB.class);
    assertEquals(testA.getOne(), testB.getOne());
    assertEquals(testA.getOneA(), testB.getOneB());
  }

  @Test
  public void testSubclassSource() {
    TestA testA = new TestA() {
    }; // anonymous subclass
    testA.setOne("one");
    testA.setOneA("oneA");
    testB = mapper.map(testA, TestB.class);
    assertEquals(testA.getOne(), testB.getOne());
    assertEquals(testA.getOneA(), testB.getOneB());
  }

  @Test
  public void testReference() {
    testA.setTestReference(Reference.FOO);
    testB = mapper.map(testA, TestB.class);
    assertEquals(testA.getOne(), testB.getOne());
    assertEquals(testA.getOneA(), testB.getOneB());
    assertEquals(testA.getTestReference(), testB.getTestReference());
  }

  @Test
  public void testReferenceSubclassSource() {
    TestA testASubclass = new TestA() {
    }; // anonymous subclass
    testASubclass.setTestReference(Reference.FOO);
    testB = mapper.map(testASubclass, TestB.class);
    assertEquals(testASubclass.getOne(), testB.getOne());
    assertEquals(testASubclass.getOneA(), testB.getOneB());
    assertEquals(testASubclass.getTestReference(), testB.getTestReference());
  }

}
