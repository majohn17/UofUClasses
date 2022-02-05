.data
	prompt: .asciiz "Enter n \n"
.text
main:	li $v0, 4	# Obtains n from the user and calls series
   	la $a0, prompt
    	syscall
    	li $v0, 5	
    	syscall
    	move $a0, $v0
    	jal series
    	j exit
	
series:	addi $sp, $sp, -8 # Recursively obtains the correct value for series
	sw $ra, 4($sp) 
	move $v0, $a0
	blt $a0, 2, base
	sw $a0, 0($sp)
	addi $a0, $a0, -1
	jal series # series(n-1)
	lw $a0, 0($sp)
	sw $v0, 0($sp)
	addi $a0, $a0, -2
	jal series # series(n-2)
	lw $v1, 0($sp)
	mul $v0, $v0, 2
	add $v0, $v0, $v1

base:	lw $ra, 4($sp)	# Base case of series, restores ra and sp and returns
	addi $sp, $sp, 8
	jr $ra
	
exit: 	add $t0, $zero, $v0 # Print value and exit
	li $v0, 1
	add $a0, $t0, $zero
	syscall
	
	li $v0, 10
	syscall
	
