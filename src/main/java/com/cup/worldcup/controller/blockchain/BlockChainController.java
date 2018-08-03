package com.cup.worldcup.controller.blockchain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/blockchain")
public class BlockChainController {

    @GetMapping("/wallet")
    public String wallet() {
        return "blockchain/wallet";
    }
}
