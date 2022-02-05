.data
prompt:	.asciiz  "Enter n \n"

.text
main:	addi $v0, $zero, 4
   	lui $a0, 0x1001		# The address of the beginning of the data section
   	syscall
   	addi $v0, $zero, 5	# Obtain integer input into $v0
   	syscall
   	addi $s0, $v0, 0	# Store n
   	addi $v0, $zero, 11	# Three lines w/ syscall to print new line for better formatting
	addi $a0, $zero, 10
	syscall
   	addi $t0, $zero, 0	# Set i before the for loop
   	j for

for: 	slt $t1, $t0, $s0
	beq $t1, $zero, exit
	addi $t1, $zero, 0	# Set j before the nested loop
	addi $t0, $t0, 1	# Increment i before nested loop to account 1 -> n rather than 0 -> n-1
	jal for2
	j for
	
for2:	slt $t2, $t1, $t0
	beq $t2, $zero, return
	addi $v0, $zero, 1
	addi $a0, $t1, 1
	syscall
	addi $v0, $zero, 11	# Print character code
	addi $a0, $zero, 32	# Ascii value for space
	syscall
	addi $t1, $t1, 1
	j for2

return: addi $v0, $zero, 11
	addi $a0, $zero, 10	# Ascii value for newline
	syscall
	jr $ra

exit: 	addi $v0, $zero, 10
	syscall
	
   	 
