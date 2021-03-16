package ptc.springframework.publictransportrest.mapper;

import contract.ticket.model.BalanceModel;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class AccountMapperTest {

    private AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);

    @Test
    void toBalanceModel() {
        Long balance = 567L;

        BalanceModel balanceModel = accountMapper.toBalanceModel(balance);

        assertThat(Long.valueOf(balanceModel.getBalance()), is(balance));
    }
}