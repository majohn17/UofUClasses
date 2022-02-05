main:		addi $a0, $zero, 0

for: 		slti $t1, $a0, 10 # First for loop that calls intialize on all array values
		beq $t1, $zero, reset
		jal initialize
		
initialize: 	addi $t1, $zero, 10 # The initialize procedure
		mul $t2, $a0, 4
		add $t2, $t2, $gp
		sw $t1, 0($t2)
		addi $a0, $a0, 1
		j for
		
reset:		addi $a0, $zero, 0 # Setting i to 0 again for the next for loop

for2:		slti $t1, $a0, 10 # Second for loop that calls decrement on all array values
		beq $t1, $zero, exit
		jal decrement
		
decrement:	addi $t1, $zero, 5 # The decrement procedure
		mul $t2, $a0, 4
		add $t2, $t2, $gp
		lw $t3, 0($t2)
		sub $t3, $t3, $t1
		sw $t3, 0($t2)
		addi $a0, $a0, 1
		j for2
		
exit:		li $v0, 10
		syscall
