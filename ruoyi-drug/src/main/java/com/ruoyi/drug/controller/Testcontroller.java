package com.ruoyi.drug.controller;

import com.ruoyi.common.core.controller.BaseController;
import org.hyperledger.fabric.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/drug")
public class Testcontroller extends BaseController {
    @Resource
    private Contract contract;

    @Resource
    private Network network;

    @PostMapping("/getdata")
    public String test1() throws EndorseException, CommitException, SubmitException, CommitStatusException {
        
        System.out.println("drug/getdata");
        byte [] res = new byte[0];

        try {
            res=contract.submitTransaction("QueryAllInfo");
        } catch (EndorseException e) {
            System.out.println(e);
        } catch (CommitException e) {
            System.out.println(e);
        } catch (SubmitException e) {
            System.out.println(e);
        } catch (CommitStatusException e) {
            System.out.println(e);
        }
        return new String(res, StandardCharsets.UTF_8);
    }

}
