package ptc.springframework.publictransportrest.controller;

import contract.ticket.api.AccountApi;
import contract.ticket.model.BalanceModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ptc.springframework.publictransportrest.mapper.AccountMapper;
import ptc.springframework.publictransportrest.service.AccountService;

import java.util.UUID;

@Slf4j
@RestController
public class AccountController implements AccountApi {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseEntity<Void> fillUserBalance(UUID userId, Long balance) {
        log.info("fillUserBalance endpoint called with userid: {}, balance: {}.", userId, balance);
        accountService.addToBalance(userId, balance);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<BalanceModel> getBalance(UUID userId) {
        log.info("getBalance endpoint called with userid: {}", userId);
        return ResponseEntity.ok(accountMapper.toBalanceModel(accountService.getUserBalance(userId)));
    }
}
