package org.aimanj.console;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.aimanj.crypto.Credentials;
import org.aimanj.crypto.WalletUtils;
import org.aimanj.ens.EnsResolver;
import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.methods.response.TransactionReceipt;
import org.aimanj.protocol.core.methods.response.AiManjClientVersion;
import org.aimanj.protocol.exceptions.TransactionException;
import org.aimanj.protocol.http.HttpService;
//import org.aimanj.protocol.infura.InfuraHttpService;
import org.aimanj.tx.Transfer;
import org.aimanj.utils.Convert;

import static org.aimanj.codegen.Console.exitError;

/**
 * Simple class for creating a wallet file.
 */
public class WalletSendFunds extends WalletManager {

    private static final String USAGE = "send <walletfile> <destination-address>";

    public static void main(String[] args) {
        if (args.length != 2) {
            exitError(USAGE);
        } else {
            new WalletSendFunds().run(args[0], args[1]);
        }
    }

    private void run(String walletFileLocation, String destinationAddress) {
        File walletFile = new File(walletFileLocation);
        Credentials credentials = getCredentials(walletFile);
        console.printf("Wallet for address " + credentials.getAddress() + " loaded\n");

        if (!WalletUtils.isValidAddress(destinationAddress)
                && !EnsResolver.isValidEnsName(destinationAddress)) {
            exitError("Invalid destination address specified");
        }

        AiManj aiManj = getMatrixClient();

        BigDecimal amountToTransfer = getAmountToTransfer();
        Convert.Unit transferUnit = getTransferUnit();
        BigDecimal amountInWei = Convert.toWei(amountToTransfer, transferUnit);

        confirmTransfer(amountToTransfer, transferUnit, amountInWei, destinationAddress);

        TransactionReceipt transactionReceipt = performTransfer(
                aiManj, destinationAddress, credentials, amountInWei);

        console.printf("Funds have been successfully transferred from %s to %s%n"
                        + "Transaction hash: %s%nMined block number: %s%n",
                credentials.getAddress(),
                destinationAddress,
                transactionReceipt.getTransactionHash(),
                transactionReceipt.getBlockNumber());
    }

    private BigDecimal getAmountToTransfer() {
        String amount = console.readLine("What amound would you like to transfer "
                + "(please enter a numeric value): ")
                .trim();
        try {
            return new BigDecimal(amount);
        } catch (NumberFormatException e) {
            exitError("Invalid amount specified");
        }
        throw new RuntimeException("Application exit failure");
    }

    private Convert.Unit getTransferUnit() {
        String unit = console.readLine("Please specify the unit (man, wei, ...) [man]: ")
                .trim();

        Convert.Unit transferUnit;
        if (unit.equals("")) {
            transferUnit = Convert.Unit.MAN;
        } else {
            transferUnit = Convert.Unit.fromString(unit.toLowerCase());
        }

        return transferUnit;
    }

    private void confirmTransfer(
            BigDecimal amountToTransfer, Convert.Unit transferUnit, BigDecimal amountInWei,
            String destinationAddress) {

        console.printf("Please confim that you wish to transfer %s %s (%s %s) to address %s%n",
                amountToTransfer.stripTrailingZeros().toPlainString(), transferUnit,
                amountInWei.stripTrailingZeros().toPlainString(),
                Convert.Unit.WEI, destinationAddress);
        String confirm = console.readLine("Please type 'yes' to proceed: ").trim();
        if (!confirm.toLowerCase().equals("yes")) {
            exitError("OK, some other time perhaps...");
        }
    }

    private TransactionReceipt performTransfer(
            AiManj aiManj, String destinationAddress, Credentials credentials,
            BigDecimal amountInWei) {

        console.printf("Commencing transfer (this may take a few minutes) ");
        try {
            Future<TransactionReceipt> future = Transfer.sendFunds(
                    aiManj, credentials, destinationAddress, amountInWei, Convert.Unit.WEI)
                    .sendAsync();

            while (!future.isDone()) {
                console.printf(".");
                Thread.sleep(500);
            }
            console.printf("$%n%n");
            return future.get();
        } catch (InterruptedException | ExecutionException | TransactionException | IOException e) {
            exitError("Problem encountered transferring funds: \n" + e.getMessage());
        }
        throw new RuntimeException("Application exit failure");
    }

    private AiManj getMatrixClient() {
        String clientAddress = console.readLine(
                "Please confirm address of running Matrix client you wish to send "
                + "the transfer request to [" + HttpService.DEFAULT_URL + "]: ")
                .trim();

        AiManj aiManj;
        if (clientAddress.equals("")) {
            aiManj = AiManj.build(new HttpService());
        }else {
            aiManj = AiManj.build(new HttpService(clientAddress));
        }

        try {
            AiManjClientVersion aiManClientVersion = aiManj.aiManClientVersion().sendAsync().get();
            if (aiManClientVersion.hasError()) {
                exitError("Unable to process response from client: "
                        + aiManClientVersion.getError());
            } else {
                console.printf("Connected successfully to client: %s%n",
                        aiManClientVersion.getAiManjClientVersion());
                return aiManj;
            }
        } catch (InterruptedException | ExecutionException e) {
            exitError("Problem encountered verifying client: " + e.getMessage());
        }
        throw new RuntimeException("Application exit failure");
    }
}
