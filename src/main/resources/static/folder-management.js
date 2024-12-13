function getDetail(element) {
    const itemId = element.getAttribute('data-id');
    const itemName = element.getAttribute('data-name');

    console.log(`Clicked item: ID = ${itemId}, Name = ${itemName}`);
}
