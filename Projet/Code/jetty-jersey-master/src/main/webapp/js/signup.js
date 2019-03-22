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
				console.log(longueurp1);
				d1=Date.parse(birth);
				d2=Date.parse(output);
				tmp=d2-d1;
				days = Math.floor(tmp / 31536000000);

				if(passe1 != passe2) {
				   swal({
                title: "ChuChuFly!",
                text: "passwords D'ont Match",
                icon: "error",
						});
				}
				 if(longueurp1 < 6){
				   swal({
                title: "ChuChuFly!",
                text: "passwords not strong enough",
                icon: "error",
						});

				}
				if(days<20 || days>80){

				   swal({
                title: "ChuChuFly!",
                text: "birth date is not allowed",
                icon: "error",
						});

				}

			}