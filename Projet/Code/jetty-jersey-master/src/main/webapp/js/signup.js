function fenvoi() {
		var birth= $("#birthDate").val();
		var d = new Date();
		var month = d.getMonth()+1;
		var day = d.getDate();
		var output = d.getFullYear() + '/' +(month<10 ? '0' : '') + month + '/' +(day<10 ? '0' : '') + day;
		  var passe1= $("#password").val();
        		  var passe2= $("#passwordConfirm").val();
        		   var longueurp1 = passe1.length;
        		   var longueurp2 = passe2.length;

			d1=Date.parse(birth);
			d2=Date.parse(output);
            tmp=d2-d1;
            days = Math.floor(tmp / 31536000000);
            if(days<20 || days>80){
            birthDate.setCustomValidity("birth date is not allowed");
            return false;

            }




		   if(longueurp1!= longueurp2){
				//longueur different
		   		return false;
		   } else if(longueurp1<6){
		   		// longueur inferieure à 6
		   		password.setCustomValidity("Passwords inférieur à 6");
		   		return false;
		   }else{
		   		if(passe1 != passe2) {
		   			 passwordConfirm.setCustomValidity("Passwords Don't Match");
		   			return false;
		   		}
		   }

		    }