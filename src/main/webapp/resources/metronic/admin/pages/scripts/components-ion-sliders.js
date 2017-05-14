var ComponentsIonSliders = function () {

    return {
        //main function to initiate the module
        init: function () {

            $("#range_1").ionRangeSlider({
                min: 0,
                max: 5000,
                from: 1000,
                to: 4000,
                type: 'double',
                step: 1,
                prefix: "$",
                prettify: false,
                hasGrid: true
            });

            $("#range_2").ionRangeSlider();

            $("#range_5").ionRangeSlider({
                min: 0,
                max: 10,
                type: 'single',
                step: 0.1,
                postfix: " mm",
                prettify: false,
                hasGrid: true
            });

            $("#range_6").ionRangeSlider({
                min: -50,
                max: 50,
                from: 0,
                type: 'single',
                step: 1,
                postfix: "°",
                prettify: false,
                hasGrid: true
            });

            $("#range_4").ionRangeSlider({
                type: "single",
                step: 100,
                postfix: " light years",
                from: 55000,
                hideText: true
            });
            
            $("#range_3").ionRangeSlider({
                type: "double",
                postfix: " miles",
                step: 10000,
                from: 25000000,
                to: 35000000,
                onChange: function(obj){
                    var t = "";
                    for(var prop in obj) {
                        t += prop + ": " + obj[prop] + "\r\n";
                    }
                    $("#result").html(t);
                }
            });

            $("#range_pract_pct").ionRangeSlider({
                type: "single",
                step: 1,
                postfix: " %",
                min: 0,
                to:100,
                max: 100,
                hideText: true,
                onChange: function(obj) {
                	var chSum = $("#chSum").val();
					var csv = chSum % 2;
					if(csv <= 1) chSum = parseInt(chSum);
					if(csv > 1) chSum = parseInt(chSum) + 1;	// 向上取整
					if(chSum % 2 != 0){
						chSum = Math.round(chSum) + 1;			// 向上取偶
					}
					
					
                	var pch = chSum * obj.fromNumber / 100;
					var pv = pch % 2;
					if(pv <= 1) pch = parseInt(pch);
					if(pv > 1) pch = parseInt(pch) + 1;		// 向上取整
					if(pch%2 != 0){
        				pch = Math.round(pch) + 1;			// 向上取偶
        			}
                	
                	$("#pch").val(pch);
                	$("#rch").val(chSum - pch);
                }
            });

            $("#updateLast").on("click", function(){

                $("#range_3").ionRangeSlider("update", {
                    min: Math.round(10000 + Math.random() * 40000),
                    max: Math.round(200000 + Math.random() * 100000),
                    step: 1,
                    from: Math.round(40000 + Math.random() * 40000),
                    to: Math.round(150000 + Math.random() * 80000)
                });

            });
            
        }

    };

}();