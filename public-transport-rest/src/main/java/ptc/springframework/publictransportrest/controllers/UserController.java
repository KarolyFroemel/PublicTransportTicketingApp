package ptc.springframework.publictransportrest.controllers;

import contract.ticket.api.UserApi;
import contract.ticket.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<List<UserHistoryModel>> searchInUserHistory(Integer xPage, Integer xSize, @Valid UserHistorySearchModel userHistorySearchModel) {
        Page<UserHistoryModel> page = userService.searchHistory(
                xPage,
                xSize,
                userHistorySearchModel);

        return ControllerHelper.buildPartialResponse(xPage, xSize, page);
    }

}
