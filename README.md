## Vending Machine

**Application Requirements**

Implement a traditional vending machine which:
* Allows users to select products - the menu is displayed in the console.
* Allows users to select the coins to insert - menu is displayed in the console.
* It Accepts coins of 1, 5, 10, 25 cents i.e. penny, nickel, dime, and quarter.
* Allows user to take refund by canceling the request.
* Returns selected product and remaining change if any.
* The state of the vending machine is saved in a file on the disk - a json file.
* Vending Machine has the product menu configurable - new products can be added in the json file.
* Vending Machine is configurable on what coins it accepts - new coins can be added in the json file.

**EVALUATION REQUIREMENTS**

You are required to implement at least:
- _IVendingMachine_ – an interface that defines the public API, all high-level functionalities.
- _VendingMachineImpl_ – the concrete implementation, the business logic.
- _Coin_ – an enum that represents all the coins supported by the Vending Machine.
- _Item_ – a class that models an item soled by the Vending Machine.
- _VendingStorage_ – a class that represents the storage area of a Vending Machine where the items are stored (you should choose the right Java Collection for this job).
- _NotFullPaidException_ – an Exception thrown by Vending Machine when a user tries to collect an item, without paying the full amount.
- _NotSufficientChangeException_ – Vending Machine throws this exception to indicate that it doesn't have sufficient change to complete this request.
- _SoldOutExcepiton_ – Vending Machine throws this exception if the user request for a product which is sold out.
