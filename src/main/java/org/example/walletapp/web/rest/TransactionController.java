package org.example.walletapp.web.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.walletapp.domain.Transaction;
import org.example.walletapp.domain.TransactionType;
import org.example.walletapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Records a transaction", description = "", tags = {}, security = {
            @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction recorded", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Transaction.class))),

            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)})

    @PostMapping("/record")
    public ResponseEntity<Transaction> recordTransaction(
            @RequestParam Long accountId,
            @RequestParam BigDecimal amount,
            @RequestParam TransactionType type) {
        Transaction recordedTransaction = transactionService.recordTransaction(accountId, amount, type);
        return ResponseEntity.ok(recordedTransaction);
    }

    @Operation(summary = "Retrieves a list of transactions", description = "", tags = {}, security = {
            @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of users", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Transaction.class))))})

    @GetMapping("/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionHistory(accountId);
        return ResponseEntity.ok(transactions);
    }
}
