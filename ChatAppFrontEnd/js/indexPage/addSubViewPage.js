/**
 * Append sub webview to the index Page
 */
mui.plusReady(function(){
	
	// get the current web view object
	let indexPageWebView = plus.webview.currentWebview();
	
	// append 4 subpage webviews to main page web view and hidden them at beginning
	for(let subPage of FrontPageWebView){
		let subPageWebView = plus.webview.create(subPage.pageUrl,subPage.pageId,
			FrontPageWebViewStyle);
		subPageWebView.hide()
		indexPageWebView.append(subPageWebView);
	}
	
	// show the default contact page
	plus.webview.show(FrontPageWebView[0].pageId);
})
		
/**
 *  bind tap event on Tabs, show the active WebView and hide others
 */
mui.plusReady(function(){
	mui('.mui-bar-tab').on('tap','.mui-tab-item',function(event){
		
		//get active tab index 
		let tabIndex = this.getAttribute('tabIndex')
		
		//show that page 
		plus.webview.show(FrontPageWebView[tabIndex].pageId,'fade-in',100)
		
		
		
		//hide other pages
		FrontPageWebView.filter( (webView, index) => {
			return index != tabIndex
		}).map((webView) =>{
			plus.webview.hide(webView.pageId,'fade-out',100)
		})
		
		
	})
})	