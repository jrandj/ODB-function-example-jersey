create or replace 
FUNCTION checkForPalindrome(inputString VARCHAR2)
   RETURN VARCHAR2 
   IS result VARCHAR2(75);
   reversedString VARCHAR2(50); 
   BEGIN 
      SELECT REVERSE(inputString) INTO reversedString FROM DUAL;
      -- Using UPPER to ignore case sensitivity.
      IF UPPER(inputString) = UPPER(reversedString)
      THEN
      RETURN(inputString||' IS a palindrome.');
      END IF;
      RETURN (inputString||' IS NOT a palindrome.');
    END checkForPalindrome;