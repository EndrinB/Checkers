# Coffee_Machine
Project 6.11.9

Write an interface specfication for a model class that represents a vending machine. In exchange for money, the machine distributes coffee or tea. When you design the specification, consider the machine's attributes first: the quantities of coffee, tea, and excess change the machine holds. (Don't forget to remember the amount of money a customer has inserted!) When you design the machine's methods, consider the machine's responsibilities to give coffee, tea, and change, to refund a customer's coins when asked, and to refuse service if no coffee or tea is left.

Two of the methods might be
insertMoney(int amount) 	the customer inserts the amount of money into the vending machine, and the machine remembers this.

askForCoffee(): boolean 	the customer requests a cup of coffee. If the customer has already inserted an adequate amount of money, and the machine still has coffee in it, the machine produces a cup of coffee, which it signifies by returning the answer, true. If the machine is unable to produce a cup of coffee, false is returned as the answer.

Based on your specification, write a model class.

Next, write an output-view of the vending machine and write an input-view and controller that help a user type commands to ``insert'' money and ``buy'' coffee and tea from the machine. 

