package me.interpretme.controller;


import  org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

public class InterpreterControllerTest extends AbstractTest {
    @Override
    @Before
    public void setUp()
    {
        super.setUp();
    }

    @Test
    public void executeCorrectPythonInstruction() throws Exception
    {
        String uri = "/interpreter/execute";
        Map<String, String> entries = new HashMap<>();
        entries.put("code", "%python print 1+1");
        String inputJson = super.mapToJson(entries);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int statusCode = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, statusCode);
        String outputJson = mvcResult.getResponse().getContentAsString();
        Map<String, String > results = super.mapFromJson(outputJson, Map.class);

        Assert.assertEquals(2, results.size());
        Assert.assertTrue(results.containsKey("result"));
        System.out.println(results.get("result"));
        Assert.assertEquals("2", results.get("result"));

        Assert.assertTrue(results.containsKey("sessionId"));
        Assert.assertNotEquals("", results.get("sessionId"));
     }

     @Test
     public void declarePythonVariableAndPrintIt() throws Exception{
        // Declare variable a = 1
         String uri = "/interpreter/execute";
         Map<String, String> entries = new HashMap<>();
         entries.put("code", "%python a = 1");
         String inputJson = super.mapToJson(entries);
         MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                 .contentType(MediaType.APPLICATION_JSON_VALUE)
                 .content(inputJson)).andReturn();
         int statusCode = mvcResult.getResponse().getStatus();
         Assert.assertEquals(200, statusCode);
         String outputJson = mvcResult.getResponse().getContentAsString();
         Map<String, String > results = super.mapFromJson(outputJson, Map.class);

         Assert.assertEquals(2, results.size());
         Assert.assertTrue(results.containsKey("result"));
         System.out.println(results.get("result"));
         Assert.assertEquals("", results.get("result"));

         Assert.assertTrue(results.containsKey("sessionId"));
         Assert.assertNotEquals("", results.get("sessionId"));
         // print a value

         String sessionId = results.get("sessionId");
         entries.put("code", "%python print a");
         entries.put("sessionId", sessionId);
         inputJson = super.mapToJson(entries);
         mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                 .contentType(MediaType.APPLICATION_JSON_VALUE)
                 .content(inputJson)).andReturn();
          statusCode = mvcResult.getResponse().getStatus();
         Assert.assertEquals(200, statusCode);
          outputJson = mvcResult.getResponse().getContentAsString();
         results = super.mapFromJson(outputJson, Map.class);

         Assert.assertEquals(2, results.size());
         Assert.assertTrue(results.containsKey("result"));
         Assert.assertEquals("1", results.get("result"));
         Assert.assertTrue(results.containsKey("sessionId"));
         Assert.assertEquals(sessionId, results.get("sessionId"));


     }

    @Test
    public void executeWrongInterpreter() throws Exception
    {
        String uri = "/interpreter/execute";
        Map<String, String> entries = new HashMap<>();
        entries.put("code", "%wrong_interpreter print 1+1");
        String inputJson = super.mapToJson(entries);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int statusCode = mvcResult.getResponse().getStatus();
        Assert.assertEquals(500, statusCode);
        String outputJson = mvcResult.getResponse().getContentAsString();
        Map<String, String > results = super.mapFromJson(outputJson, Map.class);

        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.containsKey("error"));
        System.out.println(results.get("result"));
        Assert.assertEquals("interpreter not found either it is miswritten or not supported yet",
                results.get("error"));


    }

    @Test
    public void executeUnparseableCode() throws Exception
    {
        String uri = "/interpreter/execute";
        Map<String, String> entries = new HashMap<>();
        entries.put("code", "$python print 1+1");
        String inputJson = super.mapToJson(entries);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int statusCode = mvcResult.getResponse().getStatus();
        Assert.assertEquals(500, statusCode);
        String outputJson = mvcResult.getResponse().getContentAsString();
        Map<String, String > results = super.mapFromJson(outputJson, Map.class);

        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.containsKey("error"));
        System.out.println(results.get("result"));
        Assert.assertEquals("code cannot be parsed", results.get("error"));


    }
}
