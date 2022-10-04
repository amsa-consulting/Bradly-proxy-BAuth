package com.bradlyproxy.bradlyproxybauth.controller;

import com.bradlyproxy.bradlyproxybauth.model.Cashnet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class bradleyController {

    public bradleyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    private final String BASE_URL = "https://athena.bradley.edu:3000";
    private final RestTemplate restTemplate;
    Logger logger = LoggerFactory.getLogger(bradleyController.class);
    @GetMapping("/test")
    public String test(){
        return "Test Successful";
    }
    @PostMapping({"/connection_test"})
    public String testConnection() throws Exception {

        String requestUrl = BASE_URL+"/connection_test";
        if (requestUrl == null) {
            throw new Exception("No request url is specified");
        }
        logger.info(requestUrl);
        //requestUrl = requestUrl;
        //System.out.println(requestUrl);

        // return restTemplate.getForObject(requestUrl,String.class);
        return restTemplate.postForObject(requestUrl,null,String.class);
    }
    /*
    // @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @GetMapping({"/note"})
    //public JsonNode getResultFunds(@RequestParam String cust_code) throws Exception {
    public @ResponseBody
    Object getNotes(@RequestParam String cust_code) throws Exception {
        logger.info("getResult Method is Accessed");
        String requestUrl = BASE_URL+"/note"+"?cust_code="+cust_code;
        if (requestUrl == null) {
            throw new Exception("No request url is specified");
        }
        logger.info(requestUrl);
        //requestUrl = requestUrl;
        //System.out.println(requestUrl);

        // return restTemplate.getForObject(requestUrl,String.class);
        return restTemplate.getForObject(requestUrl, JsonNode.class);
        // return requestUrl;

    }
    @GetMapping({"/demography"})
    public @ResponseBody Object getDemography(@RequestParam("cust_code2") String cust_code2) throws Exception {
        logger.info("getResult Method is Accessed");
        String requestUrl = BASE_URL+"/demography?cust_code2="+cust_code2;
        if (requestUrl == null) {
            throw new Exception("No request url is specified");
        }
        logger.info(requestUrl);
        //requestUrl = requestUrl;
        //System.out.println(requestUrl);

        // return restTemplate.getForObject(requestUrl,String.class);
        return restTemplate.getForObject(requestUrl,JsonNode.class);
        // return requestUrl;

    }
    @GetMapping({"/balance"})
    public @ResponseBody Object getStudentBalance(@RequestParam("cust_code") String cust_code) throws Exception {
        logger.info("getResult Method is Accessed");
        String requestUrl = BASE_URL+"/balance?cust_code="+cust_code;
        if (requestUrl == null) {
            throw new Exception("No request url is specified");
        }
        logger.info(requestUrl);
        //requestUrl = requestUrl;
        //System.out.println(requestUrl);

        // return restTemplate.getForObject(requestUrl,String.class);
        return restTemplate.getForObject(requestUrl,JsonNode.class);
        // return requestUrl;

    }
    */
    @RequestMapping(path = "/pull", method = RequestMethod.POST,produces = { "application/xml" })
    public //@ResponseBody
    String getData(@RequestParam String cust_code2) {

        String requestUrl = BASE_URL+"/pull?cust_code2="+cust_code2;

        logger.info("requestUrl_note {}",requestUrl.toString());
        logger.info("cust_code2 {}",cust_code2.toString());


        return restTemplate.postForObject(requestUrl,null,String.class);

    }
    //for test the produces={"application/xml"} changed to {"application/json"}
    @PostMapping(value ="push1"
            ,consumes = {"application/xml"}
            ,produces = {"application/json"}
            )

    public @ResponseBody
    String getStudentBalance1(@RequestBody Cashnet cashnet) throws Exception {

        String requestUrl  = BASE_URL + "/push";
        HttpHeaders httpHeaders = new HttpHeaders();
       // httpHeaders.set("Authorization", "Basic " + encodedCredentials);
        //httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
         /*
        Cashnet body=new Cashnet();
         body.setCust_code(cashnet.getCust_code());
         body.setDescription(cashnet.getDescription());
         body.setTerm_code(cashnet.getTerm_code());
         body.setBillno(cashnet.getBillno());
         body.setBusdate(cashnet.getBusdate());
         body.setAmount(cashnet.getAmount());
         body.setBatchno(cashnet.getBatchno());
         body.setCctype(cashnet.getCctype());
         body.setPaymenttype(cashnet.getPaymenttype());
         body.setPaymentcode(cashnet.getPaymentcode());

          */
        Map<String, Object> bodyParamMap = new HashMap<>();

        bodyParamMap.put("cust_code", cashnet.getCust_code());
        bodyParamMap.put("description", cashnet.getDescription());
        bodyParamMap.put("term_code", cashnet.getTerm_code());
        bodyParamMap.put("billno", cashnet.getBillno());
        bodyParamMap.put("busdate", cashnet.getBusdate());
        bodyParamMap.put("amount", String.valueOf(cashnet.getAmount()));
        bodyParamMap.put("batchno", cashnet.getBatchno());
        bodyParamMap.put("cctype", cashnet.getCctype());
        bodyParamMap.put("paymenttype", cashnet.getPaymenttype());
        bodyParamMap.put("paymentcode", cashnet.getPaymentcode());

      //  ObjectMapper mapper = new ObjectMapper();
        //JsonNode jsonNode = mapper.valueToTree(bodyParamMap);

        ObjectMapper Obj = new ObjectMapper();
        String jsonStr ="";
        try {
            // Converting the Java object into a JSON string
            jsonStr = Obj.writeValueAsString(bodyParamMap);
            // Displaying Java object into a JSON string
            //System.out.println(jsonStr);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        logger.info(jsonStr);

        HttpEntity<String> httpEntity = new HttpEntity<String>(jsonStr,httpHeaders);
        //HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(bodyParamMap, httpHeaders);
       // logger.info(jsonStr);
        //logger.info(httpEntity.toString());
        // transactTransactionalImport;
        return restTemplate.exchange(requestUrl, HttpMethod.POST, httpEntity,String.class).getBody();
        //logger.info(jsonNode.toString());
        //return restTemplate.postForEntity(requestUrl, jsonStr,Cashnet.class).getBody();
       // return jsonStr;
        //ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, httpEntity, String.class);
        // check response
    /*
        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println(response.getBody());
            return  "Request Successful";

        } else {
            System.out.println(response.getStatusCode());
            return "Request Failed";

        }
        */

    }
    @PostMapping(value ="push"
            //,consumes = {"application/xml"}
            //,produces = {"application/json"}
            ,produces="text/html"
    )

    public @ResponseBody
    String getStudentBalance2(@RequestParam Integer cust_code,
                              @RequestParam String description,
                              @RequestParam String term_code,
                              @RequestParam String billno,
                              @RequestParam String busdate,
                              @RequestParam Double amount,
                              @RequestParam String batchno,
                              @RequestParam String cctype,
                              @RequestParam String paymenttype,
                              @RequestParam String paymentcode
                              ) throws Exception {

        String requestUrl  = BASE_URL + "/push";
        HttpHeaders httpHeaders = new HttpHeaders();
        // httpHeaders.set("Authorization", "Basic " + encodedCredentials);
        //httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> bodyParamMap = new HashMap<>();

        bodyParamMap.put("cust_code", String.valueOf(cust_code));
        bodyParamMap.put("description", description);
        bodyParamMap.put("term_code", term_code);
        bodyParamMap.put("billno", billno);
        bodyParamMap.put("busdate", busdate);
        bodyParamMap.put("amount", String.valueOf(amount));
        bodyParamMap.put("batchno", batchno);
        bodyParamMap.put("cctype", cctype);
        bodyParamMap.put("paymenttype", paymenttype);
        bodyParamMap.put("paymentcode", paymentcode);

        //  ObjectMapper mapper = new ObjectMapper();
        //JsonNode jsonNode = mapper.valueToTree(bodyParamMap);

        ObjectMapper Obj = new ObjectMapper();
        String jsonStr ="";
        try {
            // Converting the Java object into a JSON string
            jsonStr = Obj.writeValueAsString(bodyParamMap);
            // Displaying Java object into a JSON string
            //System.out.println(jsonStr);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        logger.info(jsonStr);

        HttpEntity<String> httpEntity = new HttpEntity<String>(jsonStr,httpHeaders);
        //HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(bodyParamMap, httpHeaders);
        // logger.info(jsonStr);
        //logger.info(httpEntity.toString());
        // transactTransactionalImport;
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, httpEntity,String.class);

        //logger.info(jsonNode.toString());
        //return restTemplate.postForEntity(requestUrl, jsonStr,Cashnet.class).getBody();
        // return jsonStr;
        //ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, httpEntity, String.class);
        // check response

        if (response.getStatusCode() == HttpStatus.OK) {
            //System.out.println(response.getBody());
            return  "<cashnet>result=0&respmessage=Successfully Posted</cashnet>";

        } else {
            //System.out.println(response.getStatusCode());
            return "<cashnet>result=100&respmessage=Failed Posted</cashnet>";

        }
    }

    //BELOW ARE ENDPOINTS FOR TEST, Bradley wants to have prod and test enviroment to go side by side so instead of
    //setting a profile in the application.properties which would only enable us to run a single profile at a time
    //we've setup a separate endpoint for each prod and test

    @RequestMapping(path = "/pull_test", method = RequestMethod.POST,produces = { "application/xml" })
    public //@ResponseBody
    String getData_test(@RequestParam String cust_code2) {

        String requestUrl = BASE_URL+"/pull_test?cust_code2="+cust_code2;

        logger.info("requestUrl_note {}",requestUrl.toString());
        logger.info("cust_code2 {}",cust_code2.toString());


        return restTemplate.postForObject(requestUrl,null,String.class);

    }

    @PostMapping(value ="push_test"
            //,consumes = {"application/xml"}
            //,produces = {"application/json"}
            ,produces="text/html"
    )

    public @ResponseBody
    String getStudentBalance2_test(@RequestParam Integer cust_code,
                              @RequestParam String description,
                              @RequestParam String term_code,
                              @RequestParam String billno,
                              @RequestParam String busdate,
                              @RequestParam Double amount,
                              @RequestParam String batchno,
                              @RequestParam String cctype,
                              @RequestParam String paymenttype,
                              @RequestParam String paymentcode
    ) throws Exception {

        String requestUrl  = BASE_URL + "/push_test";
        HttpHeaders httpHeaders = new HttpHeaders();
        // httpHeaders.set("Authorization", "Basic " + encodedCredentials);
        //httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        Map<String, Object> bodyParamMap = new HashMap<>();

        bodyParamMap.put("cust_code", String.valueOf(cust_code));
        bodyParamMap.put("description", description);
        bodyParamMap.put("term_code", term_code);
        bodyParamMap.put("billno", billno);
        bodyParamMap.put("busdate", busdate);
        bodyParamMap.put("amount", String.valueOf(amount));
        bodyParamMap.put("batchno", batchno);
        bodyParamMap.put("cctype", cctype);
        bodyParamMap.put("paymenttype", paymenttype);
        bodyParamMap.put("paymentcode", paymentcode);

        //  ObjectMapper mapper = new ObjectMapper();
        //JsonNode jsonNode = mapper.valueToTree(bodyParamMap);

        ObjectMapper Obj = new ObjectMapper();
        String jsonStr ="";
        try {
            // Converting the Java object into a JSON string
            jsonStr = Obj.writeValueAsString(bodyParamMap);
            // Displaying Java object into a JSON string
            //System.out.println(jsonStr);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        logger.info(jsonStr);

        HttpEntity<String> httpEntity = new HttpEntity<String>(jsonStr,httpHeaders);
        //HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(bodyParamMap, httpHeaders);
        // logger.info(jsonStr);
        //logger.info(httpEntity.toString());
        // transactTransactionalImport;
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, httpEntity,String.class);

        //logger.info(jsonNode.toString());
        //return restTemplate.postForEntity(requestUrl, jsonStr,Cashnet.class).getBody();
        // return jsonStr;
        //ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, httpEntity, String.class);
        // check response

        if (response.getStatusCode() == HttpStatus.OK) {
            //System.out.println(response.getBody());
            return  "<cashnet>result=0&respmessage=Successfully Posted</cashnet>";

        } else {
            //System.out.println(response.getStatusCode());
            return "<cashnet>result=100&respmessage=Failed Posted</cashnet>";

        }
    }
}
