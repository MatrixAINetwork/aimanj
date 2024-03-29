package org.aimanj.protocol.scenarios;

import java.math.BigInteger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import org.junit.Test;

import org.aimanj.generated.HumanStandardToken;
import org.aimanj.protocol.core.DefaultBlockParameterName;
import org.aimanj.protocol.core.methods.response.TransactionReceipt;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.aimanj.generated.HumanStandardToken.ApprovalEventResponse;
import static org.aimanj.generated.HumanStandardToken.TransferEventResponse;
import static org.aimanj.generated.HumanStandardToken.deploy;
import static org.aimanj.tx.TransactionManager.DEFAULT_POLLING_FREQUENCY;

/**
 * Generated HumanStandardToken integration test for all supported scenarios.
 */
public class HumanStandardTokenGeneratedIT extends Scenario {

    @Test
    public void testContract() throws Exception {
        BigInteger aliceQty = BigInteger.valueOf(1_000_000);
        final String aliceAddress = ALICE.getAddress();
        final String bobAddress = BOB.getAddress();

        HumanStandardToken contract = deploy(aiManj, ALICE,
                GAS_PRICE, GAS_LIMIT,
                aliceQty, "aimanj tokens",
                BigInteger.valueOf(18), "w3j$").send();

        assertTrue(contract.isValid());

        assertThat(contract.totalSupply().send(), equalTo(aliceQty));

        assertThat(contract.balanceOf(ALICE.getAddress()).send(),
                equalTo(aliceQty));

        // CHECKSTYLE:OFF
        CountDownLatch transferEventCountDownLatch = new CountDownLatch(2);
        Disposable transferEventSubscription = contract.transferEventFlowable(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST).subscribe(
                transferEventResponse -> transferEventCountDownLatch.countDown()
        );

        CountDownLatch approvalEventCountDownLatch = new CountDownLatch(1);
        Disposable approvalEventSubscription = contract.approvalEventFlowable(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST).subscribe(
                transferEventResponse -> transferEventCountDownLatch.countDown()
        );
        // CHECKSTYLE:ON

        // transfer tokens
        BigInteger transferQuantity = BigInteger.valueOf(100_000);

        TransactionReceipt aliceTransferReceipt = contract.transfer(
                BOB.getAddress(), transferQuantity).send();

        TransferEventResponse aliceTransferEventValues =
                contract.getTransferEvents(aliceTransferReceipt).get(0);

        assertThat(aliceTransferEventValues._from,
                equalTo(aliceAddress));
        assertThat(aliceTransferEventValues._to,
                equalTo(bobAddress));
        assertThat(aliceTransferEventValues._value,
                equalTo(transferQuantity));

        aliceQty = aliceQty.subtract(transferQuantity);

        BigInteger bobQty = BigInteger.ZERO;
        bobQty = bobQty.add(transferQuantity);

        assertThat(contract.balanceOf(ALICE.getAddress()).send(),
                equalTo(aliceQty));
        assertThat(contract.balanceOf(BOB.getAddress()).send(),
                equalTo(bobQty));

        // set an allowance
        assertThat(contract.allowance(
                aliceAddress, bobAddress).send(),
                equalTo(BigInteger.ZERO));

        transferQuantity = BigInteger.valueOf(50);
        TransactionReceipt approveReceipt = contract.approve(
                BOB.getAddress(), transferQuantity).send();

        ApprovalEventResponse approvalEventValues =
                contract.getApprovalEvents(approveReceipt).get(0);

        assertThat(approvalEventValues._owner,
                equalTo(aliceAddress));
        assertThat(approvalEventValues._spender,
                equalTo(bobAddress));
        assertThat(approvalEventValues._value,
                equalTo(transferQuantity));

        assertThat(contract.allowance(
                aliceAddress, bobAddress).send(),
                equalTo(transferQuantity));

        // perform a transfer as Bob
        transferQuantity = BigInteger.valueOf(25);

        // Bob requires his own contract instance
        HumanStandardToken bobsContract = HumanStandardToken.load(
                contract.getContractAddress(), aiManj, BOB, STATIC_GAS_PROVIDER);

        TransactionReceipt bobTransferReceipt = bobsContract.transferFrom(
                aliceAddress,
                bobAddress,
                transferQuantity).send();

        TransferEventResponse bobTransferEventValues =
                contract.getTransferEvents(bobTransferReceipt).get(0);
        assertThat(bobTransferEventValues._from,
                equalTo(aliceAddress));
        assertThat(bobTransferEventValues._to,
                equalTo(bobAddress));
        assertThat(bobTransferEventValues._value,
                equalTo(transferQuantity));

        aliceQty = aliceQty.subtract(transferQuantity);
        bobQty = bobQty.add(transferQuantity);

        assertThat(contract.balanceOf(aliceAddress).send(),
                equalTo(aliceQty));
        assertThat(contract.balanceOf(bobAddress).send(),
                equalTo(bobQty));

        transferEventCountDownLatch.await(DEFAULT_POLLING_FREQUENCY, TimeUnit.MILLISECONDS);
        approvalEventCountDownLatch.await(DEFAULT_POLLING_FREQUENCY, TimeUnit.MILLISECONDS);

        approvalEventSubscription.dispose();
        transferEventSubscription.dispose();
        Thread.sleep(1000);
        assertTrue(approvalEventSubscription.isDisposed());
        assertTrue(transferEventSubscription.isDisposed());
    }
}
