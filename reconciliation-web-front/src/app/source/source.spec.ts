import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Source } from './source';

describe('FileUploadComponent', () => {
  let component: Source;
  let fixture: ComponentFixture<Source>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Source ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Source);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
