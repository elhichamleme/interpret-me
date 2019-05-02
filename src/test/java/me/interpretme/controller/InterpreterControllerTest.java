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
    public void executeInstruction() throws Exception
    {
        String uri = "/interpreter/execute";
        Map<String, String> entries = new HashMap<>();
        entries.put("code", "%python print 1+1");
        entries.put("sessionId", "1234");
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
        Assert.assertEquals("%python print 1+1", results.get("result"));

        Assert.assertTrue(results.containsKey("sessionId"));
        Assert.assertEquals("1234", results.get("sessionId"));


    }
}
