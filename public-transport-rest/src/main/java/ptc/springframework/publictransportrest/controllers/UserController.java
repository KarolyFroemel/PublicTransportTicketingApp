package ptc.springframework.publictransportrest.controllers;

import contract.ticket.api.UserApi;
import contract.ticket.model.AccountHistoryModel;
import contract.ticket.model.AccountHistorySearchModel;
import contract.ticket.model.FillBalanceModel;
import contract.ticket.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ptc.springframework.publictransportrest.services.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<Void> fillUserAccountBalance(@Valid FillBalanceModel fillBalanceModel) {
        userService.fillBalance(fillBalanceModel.getAddBalance());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Integer> getUserAccountBalance() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserAccountBalance());
    }

    @Override
    public ResponseEntity<UserModel> getUserInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserInfo());
    }

    @Override
    public ResponseEntity<List<AccountHistoryModel>> searchInAccountHistory(Integer xPage, Integer xSize, @Valid AccountHistorySearchModel accountHistorySearchModel) {
        return null;
    }
}
