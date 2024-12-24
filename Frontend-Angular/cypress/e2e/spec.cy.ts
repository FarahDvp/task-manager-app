describe('template spec', () => {
  it('passes', () => {
    cy.visit('https://example.cypress.io')
  })
})


describe('ListprojetComponent', () => {
  beforeEach(() => {
    cy.visit('https://localhost/app/homeAdmin/listProjet'); // Replace '/' with the actual URL of your Angular app
  });

  it('should fetch and display a list of projects from the API', () => {
    cy.get('[data-test=project-card]').should('have.length.greaterThan', 0);
  });
});
