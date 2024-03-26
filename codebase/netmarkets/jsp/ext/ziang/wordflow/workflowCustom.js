PTC.carambola = {};
PTC.carambola.twoPane = {};

/**
 * Handles all 'click' events that are emmitted from the carambola renderTwoPane example.
 * When a click event occurs in either pane of the renderTwoPane, this function is called.
 * If the user clicked on one of the buttons in the Left/Top pane, it makes an AJAX
 * call to get the data that will be rendered in the right/bottom pane.
 *
 * @param {Event} evt - the click event
 */
PTC.carambola.twoPane.onClickHandler = function (evt) {

    var button = PTC.carambola.twoPane.getChildNodeByTag(evt.target, 'BUTTON');

    if (button) {
        var cp = PTC.getCmp(PTC.navigation.cardManager);
        var contentPanel;
        var renderToDiv = 'twoPanes_RIGHTPANE2';

        // get the content pane that corresponds how the two pane component is laid out (i.e. right or bottom pane)
        if (cp.layout.activeItem.CONTENT_DIV === renderToDiv) {
            contentPanel = PTC.getCmp(PTC.navigation.contentTabID + '_twoPanes_center');
        } else {
            renderToDiv = 'twoPanes_BOTTOMPANE';
            contentPanel = PTC.getCmp(PTC.navigation.contentTabID + '_twoPanes_bottom');
        }

        // make an AJAX call to get the HTML to render in the right/bottom pane
        if (contentPanel !== undefined) {
            contentPanel.clearCardContents();

            var options = {
                asynchronous: false,
                method: 'get',
                parameters: {
                    buttonId: button.innerHTML
                }
            };
            var transport = requestHandler.doRequest(
                getBaseHref() + 'netmarkets/jsp/carambola/customization/examples/twoPanePanels/rightOrBottomPane.jsp',
                options);

            // render the HTML returned from the AJAX response to the right/bottom DIV
            var panel = new PTC.Panel({
                id: 'test', renderTo: renderToDiv, html: transport.responseText
            });
        }

        // render out the new right/bottom pane
        panel.doLayout();
    }
};

/**
 * Returns the closest child from the parent element that matches the specified tag.
 * 从与指定标记匹配的父元素中返回最接近的子元素。
 *
 * @param {Object} parentElement - The element to start from
 * @param {String} tagName - the tag to look for (i.e. INPUT, BUTTON)
 * @return The HTML element that matches the specified tag, or undefined if none is found
 */
PTC.carambola.twoPane.getChildNodeByTag = function (parentElement, tagName) {
    if (parentElement) {
        if (parentElement.tagName === tagName) {
            return parentElement;
        } else {
            var childNodes = parentElement.childNodes;
            for (var i = 0; i < childNodes.length; i++) {
                return PTC.carambola.twoPane.getChildNodeByTag(childNodes[i], tagName);
            }
        }
    }
    return undefined;
};
