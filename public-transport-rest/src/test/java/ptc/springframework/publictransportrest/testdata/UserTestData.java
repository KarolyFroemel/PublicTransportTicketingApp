package ptc.springframework.publictransportrest.testdata;

import contract.ticket.model.AccountModel;
import contract.ticket.model.FillBalanceModel;
import contract.ticket.model.UserModel;

import java.util.UUID;

public class UserTestData {

    public UserModel getUserInfoResult() {
        UserModel userModel = new UserModel();
        userModel.setId(UUID.fromString("1958fb4a-2f76-480c-8df2-6045f3c017a5"));
        userModel.setName("David Beckham");
        userModel.setEmail("david.beckham@gmail.com");
        AccountModel accountModel = new AccountModel();
        accountModel.setBalance(1000);
        userModel.setAccount(accountModel);
        return userModel;
    }

    public FillBalanceModel getFillBalanceModel(){
        FillBalanceModel fillBalanceModel = new FillBalanceModel();
        fillBalanceModel.setAddBalance(3000);
        return fillBalanceModel;
    }
}
